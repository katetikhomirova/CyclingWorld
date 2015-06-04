package ua.nure.tikhomirova.dao;

import java.util.List;

import ua.nure.tikhomirova.entity.Route;

public interface DBManager {

	public List<Route> getRoutes(String userId);

	public boolean saveRoute(Route route, String userId);

	public boolean removeRoute(Route route);

}
