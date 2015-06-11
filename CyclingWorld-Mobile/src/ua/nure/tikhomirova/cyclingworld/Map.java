package ua.nure.tikhomirova.cyclingworld;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import ua.nure.tikhomirova.cyclingworld.entity.Point;
import ua.nure.tikhomirova.cyclingworld.utils.DistanceCalculator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint({ "HandlerLeak", "SimpleDateFormat" })
public class Map extends Activity {

	private GoogleMap googleMap;
	private Marker mMarker;
	private List<Marker> route;
	// variables needed to make Http-request
	private String id;
	double distance;
	String result = "";
	String routeCoords = "";
	// emulating variables
	private Timer myTimer;
	int index;
	List<LatLng> emulatedCoords;
	String coords;
	// buttons
	Button startButton;
	Button finishButton;
	Button emulateButton;

	AlertDialog.Builder ad;

	Handler requestResultHandler;
	final String tag = "Your Logcat tag: ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestResultHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 1) {
					Intent intent = new Intent(Map.this, MainActivity.class);
					Map.this.startActivity(intent);
				}
			}
		};

		setContentView(R.layout.map_activity);
		route = new ArrayList<Marker>();
		// get intent params
		Intent intent = getIntent();
		coords = intent.getStringExtra("coordsString");
		id = intent.getStringExtra("id");
		// initialize map
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}

			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			drawPolyLine(coords);
			makeAlertDialog();
			startButton = (Button) findViewById(R.id.start);
			finishButton = (Button) findViewById(R.id.finish);
			emulateButton = (Button) findViewById(R.id.emulate);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void makeAlertDialog() {
		ad = new AlertDialog.Builder(Map.this);
		ad.setTitle(""); // заголовок
		ad.setMessage("Do you want to save your training?"); // сообщение
		ad.setPositiveButton("Yes", new OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				if (route.size() > 1) {
					for (int i = 0; i < route.size(); i++) {
						routeCoords += route.get(i).getPosition().latitude
								+ "," + route.get(i).getPosition().longitude
								+ ",";
					}
					distance = DistanceCalculator.calculate(route);
					Thread saveTrainingThread = new Thread(new Runnable() {
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
												.format(Calendar.getInstance()
														.getTime())
										+ ","
										+ routeCoords;

								Log.i(tag, "url: " + url);
								HttpGet request = new HttpGet(url);
								ResponseHandler<String> handler = new BasicResponseHandler();
								try {
									result = httpclient.execute(request,
											handler);

								} catch (ClientProtocolException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								httpclient.getConnectionManager().shutdown();
								if (result != null) {
									requestResultHandler.sendEmptyMessage(1);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							clearMap();
						}
					});
					saveTrainingThread.start();
				} else {
					Toast.makeText(Map.this, "Only one point founded!",
							Toast.LENGTH_LONG).show();
					clearMap();
				}
			}

		});
		ad.setNegativeButton("No", new OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				clearMap();
			}
		});
		ad.setCancelable(true);

	}

	private void clearMap() {
		route = new ArrayList<Marker>();
		googleMap.clear();
		Log.i(tag, "drawing polyline");
		drawPolyLine(coords);
	}

	public void start(View v) {
		startButton.setEnabled(false);
		finishButton.setEnabled(true);
		emulateButton.setEnabled(false);
		route = new ArrayList<Marker>();
		distance = 0;
		googleMap.setMyLocationEnabled(true);
		googleMap
				.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

					@Override
					public void onMyLocationChange(Location location) {
						LatLng loc = new LatLng(location.getLatitude(),
								location.getLongitude());

						if (route.size() > 0) {
							distance = DistanceCalculator
									.calculateTwoPointDistance(loc,
											route.get(route.size() - 1)
													.getPosition());
						}
						if (route.size() > 0 && distance > 0.02) {
							mMarker.remove();

							mMarker = googleMap.addMarker(new MarkerOptions()
									.position(loc));
							route.add(mMarker);
							addTempPolyLine(route.get(route.size() - 1)
									.getPosition(), route.get(route.size() - 2)
									.getPosition());
						} else if (route.size() == 0) {
							mMarker = googleMap.addMarker(new MarkerOptions()
									.position(loc));
							route.add(mMarker);
							if (googleMap != null) {
								googleMap.animateCamera(CameraUpdateFactory
										.newLatLngZoom(loc, 16.0f));
							}
						}
					}
				});
	}

	public void finish(View v) {
		startButton.setEnabled(true);
		finishButton.setEnabled(false);
		emulateButton.setEnabled(true);
		googleMap.setMyLocationEnabled(false);
		googleMap.setOnMyLocationChangeListener(null);
		ad.show();
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
				Color.rgb(39, 128, 11));
		for (Point p : coords) {
			options.add(new LatLng(p.getLat(), p.getLng()));
		}
		googleMap.addPolyline(options);
	}

	private void addTempPolyLine(LatLng start, LatLng end) {
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(end,
				googleMap.getCameraPosition().zoom));
		PolylineOptions options = new PolylineOptions().geodesic(true).color(
				Color.rgb(14, 7, 108));
		options.add(start);
		options.add(end);
		googleMap.addPolyline(options);
	}

	public void emulate(View v) {
		finishButton.setEnabled(false);
		startButton.setEnabled(false);
		emulateButton.setEnabled(false);
		emulate(coords);
	}

	// emulating methods
	public void emulate(String coords) {

		emulatedCoords = new ArrayList<LatLng>();
		String[] res = coords.split(";");
		for (int i = 0; i < res.length; i++) {
			String[] point = res[i].split(",");
			emulatedCoords.add(new LatLng(Double.parseDouble(point[0]), Double
					.parseDouble(point[1])));
		}

		myTimer = new Timer();
		index = -1;
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				index++;
				if (index < emulatedCoords.size())
					TimerMethod();
				else {
					emulateFinish();
					emulatedCoords = null;
					route = new ArrayList<Marker>();
					cancel();
				}
			}

		}, 0, 1500);

	}

	private void emulateFinish() {
		this.runOnUiThread(emulateFinished);
	}

	private Runnable emulateFinished = new Runnable() {
		public void run() {
			emulateButton.setEnabled(true);
			startButton.setEnabled(true);
			googleMap.clear();
			Log.i(tag, "drawing polyline");
			drawPolyLine(coords);
		}
	};

	private void TimerMethod() {
		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			if (route.size() > 0) {
				mMarker.remove();

				mMarker = googleMap.addMarker(new MarkerOptions()
						.position(emulatedCoords.get(index)));
				route.add(mMarker);
				addTempPolyLine(route.get(route.size() - 1).getPosition(),
						route.get(route.size() - 2).getPosition());
			} else if (route.size() == 0) {
				mMarker = googleMap.addMarker(new MarkerOptions()
						.position(emulatedCoords.get(index)));
				route.add(mMarker);

			}
			if (googleMap != null) {
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						mMarker.getPosition(), 16.0f));
			}
		}
	};

}
