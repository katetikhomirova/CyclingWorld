package ua.nure.tikhomirova.entity;

import java.util.ArrayList;
import java.util.List;

public class Training {

	private String userId;
	List<Point> coords;
	private String time;
	private String distance;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Training() {
		coords = new ArrayList<Point>();
	}

	public void addCoords(String[] arr, int from) {
		coords = new ArrayList<Point>();
		for (int i = from; i < arr.length; i += 2)
			coords.add(new Point(Double.parseDouble(arr[i]), Double
					.parseDouble(arr[i + 1])));
	}

	public void setCoordsFromString(String str) {
		coords = new ArrayList<Point>();
		String[] res = str.split(";");
		for (int i = 0; i < res.length; i++) {
			String[] point = res[i].split(",");
			coords.add(new Point(Double.parseDouble(point[0]), Double
					.parseDouble(point[1])));
		}
	}

	public String getCoordsString() {
		String res = "";
		for (int i = 0; i < coords.size(); i++)
			res += coords.get(i).toString() + ";";
		return res;
	}
}
