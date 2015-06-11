package ua.nure.tikhomirova.cyclingworld.utils;

import java.text.DecimalFormat;
import java.util.List;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class DistanceCalculator {

	public static double calculate(List<Marker> route) {
		double res = 0;
		for (int i = 0; i < route.size() - 1; i++) {
			res += calculateTwoPointDistance(route.get(i).getPosition(), route
					.get(i + 1).getPosition());
		}
		return res;
	}

	public static double calculateTwoPointDistance(LatLng start, LatLng end) {
		int earthRadius = 6371;
		double startLat = start.latitude;
		double endLat = end.latitude;
		double startLng = start.longitude;
		double endLng = end.longitude;
		double deltaLat = Math.toRadians(endLat - startLat);
		double deltaLng = Math.toRadians(endLng - startLng);

		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(Math.toRadians(startLat))
				* Math.cos(Math.toRadians(endLat)) * Math.sin(deltaLng / 2)
				* Math.sin(deltaLng / 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		double valueResult = earthRadius * c;
		double km = valueResult / 1;
		DecimalFormat newFormat = new DecimalFormat("####");
		int kmInDec = Integer.valueOf(newFormat.format(km));
		double meter = valueResult % 1000;
		int meterInDec = Integer.valueOf(newFormat.format(meter));
		Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
				+ " Meter   " + meterInDec);

		return earthRadius * c;
	}

}
