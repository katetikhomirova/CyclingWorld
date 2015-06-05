package ua.nure.tikhomirova.rest;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.tikhomirova.dao.MongoDBManager;
import ua.nure.tikhomirova.entity.Route;

@RestController
@RequestMapping(value = "/rest")
public class PolyLineService {

	@ResponseBody
	@RequestMapping(value = "/getPolyLines/{id}", method = RequestMethod.GET, produces = "application/json", headers = "Accept=application/json")
	public List<Route> getRoutes(@PathVariable String id) {
		try {
			return MongoDBManager.getInstance().getRoutes(id);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/removeRoute/{id}/{routeName}", method = RequestMethod.GET)
	public void removeRoute(@PathVariable String id,
			@PathVariable String routeName) {
		try {
			MongoDBManager.getInstance().removeRoute(
					MongoDBManager.getInstance().getRoute(id, routeName));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getRoute/{id}/{routeName}", method = RequestMethod.GET)
	public Route getRoute(@PathVariable String id,
			@PathVariable String routeName) {
		try {
			return MongoDBManager.getInstance().getRoute(id, routeName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return new Route();
	}

	@RequestMapping(value = "/savePolyLine", method = RequestMethod.GET)
	public void savePolyLine(@RequestParam(value = "line") String line) {
		if (line != null) {
			String[] res = line.split(",");

			Route route = new Route();
			route.setUserId(res[0]);
			route.setName(res[1]);
			route.setDistance(res[2]);
			route.setIsPublic(res[3]);
			route.addCoords(res, 4);

			try {
				MongoDBManager.getInstance().saveRoute(route);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

}
