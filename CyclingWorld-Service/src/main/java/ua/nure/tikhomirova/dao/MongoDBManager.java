package ua.nure.tikhomirova.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import ua.nure.tikhomirova.entity.Route;

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

}
