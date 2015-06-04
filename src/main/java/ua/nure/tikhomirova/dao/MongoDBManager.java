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
				"mongodb://CloudFoundry_501rl3a6_17378t9e_7husld51:5xLkXbDZU_W8aMiJHb01y5c4Bdbvv9dh@ds043348.mongolab.com:43348/CloudFoundry_501rl3a6_17378t9e");
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
	public boolean saveRoute(Route route) {
		DBCollection routes = db.getCollection("Routes");
		BasicDBObject newRoute = routeToDBObject(route);
		BasicDBObject[] routesToInsert = new BasicDBObject[1];
		routesToInsert[0] = newRoute;
		routes.insert(routesToInsert);
		return false;
	}

	@Override
	public boolean removeRoute(Route route) {
		return false;
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
