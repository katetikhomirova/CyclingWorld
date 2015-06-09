package ua.nure.tikhomirova.dao;

import java.util.List;

import ua.nure.tikhomirova.entity.Route;

public interface DBManager {

	public Route getRoute(String userId, String routeName);

	public List<Route> getRoutes(String userId);
	
	public List<Route> getPublicRoutes(String userId);

	public boolean saveRoute(Route route);

	public boolean removeRoute(Route route);	

}
