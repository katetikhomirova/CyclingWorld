package ua.nure.tikhomirova.cyclingworld;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import ua.nure.tikhomirova.cyclingworld.entity.Point;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends Activity {
	@Override
	protected void onStart() {
		super.onStart();
	}

	static final LatLng TutorialsPoint = new LatLng(21, 57);
	private GoogleMap googleMap;
	private Marker mMarker;
	private List<Marker> route;
	private String id;
	double distance;
	String result = "";
	Handler h;
	final String tag = "Your Logcat tag: ";
	String routeCoords = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 1) {
					Toast.makeText(getApplicationContext(),
							"Saved succesfully!", Toast.LENGTH_SHORT);
					Log.i(tag, "go to main");
					Intent intent = new Intent(Map.this, MainActivity.class);
					Map.this.startActivity(intent);
				}
			}
		};

		setContentView(R.layout.map_activity);
		route = new ArrayList<Marker>();

		Intent intent = getIntent();

		String coords = intent.getStringExtra("coordsString");
		id = intent.getStringExtra("id");
		Log.i("Your Logcat tag: ", "id in map " + id);
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}

			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			drawPolyLine(coords);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void drawPolyLine(String str) {
		List<Point> coords = new ArrayList<Point>();
		String[] res = str.split(";");
		for (int i = 0; i < res.length; i++) {
			String[] point = res[i].split(",");
			coords.add(new Point(Double.parseDouble(point[0]), Double
					.parseDouble(point[1])));
		}
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				coords.get(0).getLat(), coords.get(0).getLng()), 15));

		PolylineOptions options = new PolylineOptions().geodesic(true).color(
				Color.GREEN);
		for (Point p : coords) {
			options.add(new LatLng(p.getLat(), p.getLng()));
		}
		googleMap.addPolyline(options);
	}

	public void start(View v) {
		Button but = (Button) v;
		v.setEnabled(false);
		route = new ArrayList<Marker>();
		distance = 0;
		googleMap.setMyLocationEnabled(true);
		googleMap
				.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

					@Override
					public void onMyLocationChange(Location location) {
						LatLng loc = new LatLng(location.getLatitude(),
								location.getLongitude());
						mMarker = googleMap.addMarker(new MarkerOptions()
								.position(loc));
						route.add(mMarker);
						if (googleMap != null) {
							googleMap.animateCamera(CameraUpdateFactory
									.newLatLngZoom(loc, 16.0f));
						}
					}
				});
	}

	public void finish(View v) {
		Button but = (Button) v;
		v.setEnabled(false);
		googleMap.setOnMyLocationChangeListener(null);
		for (int i = 0; i < route.size(); i++) {
			routeCoords += route.get(i).getPosition().latitude + ","
					+ route.get(i).getPosition().longitude + ",";
		}
		distance = calculate();
		// routeCoords.
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpClient httpclient = new DefaultHttpClient();

					Log.i(tag, "routeCoodrs: " + routeCoords);
					String url = "http://cyclingworld-service.cfapps.io/rest/saveTraining?training="
							+ id
							+ ","
							+ new BigDecimal(distance).setScale(2,
									RoundingMode.UP).doubleValue()
							+ ","
							+ new SimpleDateFormat("dd.MM.yy_HH.mm")
									.format(Calendar.getInstance().getTime())
							+ "," + routeCoords;

					Log.i(tag, "url: " + url);
					HttpGet request = new HttpGet(url);
					ResponseHandler<String> handler = new BasicResponseHandler();
					try {
						result = httpclient.execute(request, handler);

					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					httpclient.getConnectionManager().shutdown();
					if (result != null) {
						h.sendEmptyMessage(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
	}

	private double calculate() {
		double res = 0;
		for (int i = 0; i < route.size() - 1; i++) {
			res += calculationByDistance(route.get(i).getPosition(),
					route.get(i + 1).getPosition());
		}
		return res;
	}

	public double calculationByDistance(LatLng StartP, LatLng EndP) {
		int Radius = 6371;// radius of earth in Km
		double lat1 = StartP.latitude;
		double lat2 = EndP.latitude;
		double lon1 = StartP.longitude;
		double lon2 = EndP.longitude;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double valueResult = Radius * c;
		double km = valueResult / 1;
		DecimalFormat newFormat = new DecimalFormat("####");
		int kmInDec = Integer.valueOf(newFormat.format(km));
		double meter = valueResult % 1000;
		int meterInDec = Integer.valueOf(newFormat.format(meter));
		Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
				+ " Meter   " + meterInDec);

		return Radius * c;
	}
}
