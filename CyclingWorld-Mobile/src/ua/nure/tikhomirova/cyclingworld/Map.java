package ua.nure.tikhomirova.cyclingworld;

import java.util.ArrayList;
import java.util.List;

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
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class Map extends Activity {
	@Override
	protected void onStart() {
		super.onStart();
		googleMap.setMyLocationEnabled(true);

	}

	static final LatLng TutorialsPoint = new LatLng(21, 57);
	private GoogleMap googleMap;
	private Marker mMarker;
	private List<Marker> route;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		route = new ArrayList<Marker>();

		Intent intent = getIntent();

		String coords = intent.getStringExtra("coordsString");
		Log.i("Your Logcat tag: ", "cords in map " + coords);
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}

			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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

		PolylineOptions options = new PolylineOptions().geodesic(true);
		for (Point p : coords) {
			options.add(new LatLng(p.getLat(), p.getLng()));
		}
		googleMap.addPolyline(options);
	}
}
