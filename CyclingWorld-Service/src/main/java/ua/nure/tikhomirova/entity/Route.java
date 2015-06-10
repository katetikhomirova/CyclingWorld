package ua.nure.tikhomirova.entity;

import java.util.ArrayList;
import java.util.List;

public class Route {

	private long id;
	List<Point> coords;
	private String name;
	private String distance;
	private String userId;
	private String isPublic;

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Point> getCoords() {
		return coords;
	}

	public void setCoords(List<Point> coords) {
		this.coords = coords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void addCoords(String[] arr, int from) {
		coords = new ArrayList<Point>();
		for (int i = from; i < arr.length; i += 2)
			coords.add(new Point(Double.parseDouble(arr[i]), Double
					.parseDouble(arr[i + 1])));
	}

	public String getCoordsString() {
		String res = "";
		for (int i = 0; i < coords.size(); i++)
			res += coords.get(i).toString() + ";";
		return res;
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

	public Route() {
		coords = new ArrayList<Point>();
	}

}
