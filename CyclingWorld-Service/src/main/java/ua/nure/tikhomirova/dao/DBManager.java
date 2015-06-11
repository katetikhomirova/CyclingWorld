package ua.nure.tikhomirova.dao;

import java.util.List;

import ua.nure.tikhomirova.entity.Route;
import ua.nure.tikhomirova.entity.Training;

public interface DBManager {

	// route methods
	public Route getRoute(String userId, String routeName);

	public List<Route> getRoutes(String userId);

	public List<Route> getPublicRoutes(String userId);

	public boolean saveRoute(Route route);

	public boolean removeRoute(Route route);

	// training methods

	public List<Training> getTrainings(String userId);

	public boolean saveTraining(Training training);

}
