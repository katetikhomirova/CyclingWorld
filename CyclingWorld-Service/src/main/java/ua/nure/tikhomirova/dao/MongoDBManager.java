package ua.nure.tikhomirova.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.tikhomirova.entity.Route;
import ua.nure.tikhomirova.entity.Training;

import com.mongodb.*;

public class MongoDBManager implements DBManager {

	private MongoClient client;

	private DB db;

	private MongoDBManager() throws UnknownHostException {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://CloudFoundry_501rl3a6_17378t9e_vsmlgr06:3Aml02MM5asftnNacN8Pkv_FsGcsOAQ1@ds043348.mongolab.com:43348/CloudFoundry_501rl3a6_17378t9e");
		client = new MongoClient(uri);
		db = client.getDB(uri.getDatabase());
	}

	private static MongoDBManager instance;

	public static synchronized MongoDBManager getInstance()
			throws UnknownHostException {
		if (instance == null) {
			instance = new MongoDBManager();
		}
		return instance;
	}

	// route methods

	@Override
	public Route getRoute(String userId, String routeName) {
		DBCollection routes = db.getCollection("Routes");
		// Generate search query
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> query = new ArrayList<BasicDBObject>();
		query.add(new BasicDBObject("userId", userId));
		query.add(new BasicDBObject("name", routeName));
		andQuery.put("$and", query);
		DBCursor docs = routes.find(andQuery);
		if (docs.hasNext())
			return dbObjectToRoute(docs.next());
		return new Route();
	}

	@Override
	public List<Route> getRoutes(String userId) {
		List<Route> res = new ArrayList<Route>();
		DBCollection routes = db.getCollection("Routes");
		BasicDBObject findQuery = new BasicDBObject("userId", userId);

		DBCursor docs = routes.find(findQuery);

		while (docs.hasNext()) {
			DBObject doc = docs.next();
			res.add(dbObjectToRoute(doc));
		}
		return res;
	}

	@Override
	public List<Route> getPublicRoutes(String userId) {
		List<Route> res = new ArrayList<Route>();
		DBCollection routes = db.getCollection("Routes");
		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> query = new ArrayList<BasicDBObject>();
		query.add(new BasicDBObject("userId", userId));
		query.add(new BasicDBObject("isPub", "true"));
		andQuery.put("$and", query);
		DBCursor docs = routes.find(andQuery);

		while (docs.hasNext()) {
			DBObject doc = docs.next();
			res.add(dbObjectToRoute(doc));
		}
		return res;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean saveRoute(Route route) {
		WriteResult result = null;
		DBCollection routes = db.getCollection("Routes");
		BasicDBObject newRoute = routeToDBObject(route);
		BasicDBObject[] routesToInsert = new BasicDBObject[1];
		routesToInsert[0] = newRoute;
		result = routes.insert(routesToInsert);
		CommandResult cmd = result.getLastError();
		if (cmd != null && !cmd.ok()) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean removeRoute(Route route) {
		DBCollection routes = db.getCollection("Routes");
		WriteResult result = null;
		result = routes.remove(routeToDBObject(route));
		CommandResult cmd = result.getLastError();
		if (cmd != null && !cmd.ok()) {
			return false;
		}
		return true;
	}

	private Route dbObjectToRoute(DBObject doc) {
		Route route = new Route();
		route.setUserId((String) doc.get("userId"));
		route.setDistance((String) doc.get("dist"));
		route.setIsPublic((String) doc.get("isPub"));
		route.setName((String) doc.get("name"));
		route.setCoordsFromString((String) doc.get("coords"));
		return route;
	}

	private BasicDBObject routeToDBObject(Route route) {
		BasicDBObject res = new BasicDBObject();
		res.put("userId", route.getUserId());
		res.put("name", route.getName());
		res.put("dist", route.getDistance());
		res.put("isPub", route.getIsPublic());
		res.put("coords", route.getCoordsString());
		return res;
	}

	// training methods

	@Override
	public List<Training> getTrainings(String userId) {
		List<Training> res = new ArrayList<Training>();
		DBCollection trainings = db.getCollection("Trainings");
		BasicDBObject findQuery = new BasicDBObject("userId", userId);

		DBCursor docs = trainings.find(findQuery);

		while (docs.hasNext()) {
			DBObject doc = docs.next();
			res.add(dbObjectToTraining(doc));
		}
		return res;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean saveTraining(Training training) {
		WriteResult result = null;
		DBCollection routes = db.getCollection("Trainings");
		BasicDBObject newTraining = trainingToDBObject(training);
		BasicDBObject[] trainingsToInsert = new BasicDBObject[1];
		trainingsToInsert[0] = newTraining;
		result = routes.insert(trainingsToInsert);
		CommandResult cmd = result.getLastError();
		if (cmd != null && !cmd.ok()) {
			return false;
		}
		return true;
	}

	private Training dbObjectToTraining(DBObject doc) {
		Training t = new Training();
		t.setUserId((String) doc.get("userId"));
		t.setDistance((String) doc.get("dist"));
		t.setTime((String) doc.get("time"));
		t.setCoordsFromString((String) doc.get("coords"));
		return t;
	}

	private BasicDBObject trainingToDBObject(Training training) {
		BasicDBObject res = new BasicDBObject();
		res.put("userId", training.getUserId());
		res.put("time", training.getTime());
		res.put("dist", training.getDistance());
		res.put("coords", training.getCoordsString());
		return res;
	}

}
