package ua.nure.tikhomirova.rest;

import java.net.UnknownHostException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.nure.tikhomirova.dao.MongoDBManager;
import ua.nure.tikhomirova.entity.Route;

@Controller
public class SavePolyLine {

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
