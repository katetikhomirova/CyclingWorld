package ua.nure.tikhomirova.rest;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ua.nure.tikhomirova.dao.MongoDBManager;
import ua.nure.tikhomirova.entity.Training;

@RestController
@RequestMapping(value = "/rest")
public class TrainingService {

	@ResponseBody
	@RequestMapping(value = "/getTrainings/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "Accept=application/json")
	public List<Training> getRoutes(@PathVariable String id) {
		try {
			return MongoDBManager.getInstance().getTrainings(id);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/saveTraining", method = RequestMethod.GET)
	public boolean saveTraining(@RequestParam(value = "training") String line) {
		if (line != null) {
			String[] res = line.split(",");

			Training t = new Training();
			t.setUserId(res[0]);
			t.setDistance(res[1]);
			t.setTime(res[2]);
			t.addCoords(res, 3);
			try {
				return MongoDBManager.getInstance().saveTraining(t);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
