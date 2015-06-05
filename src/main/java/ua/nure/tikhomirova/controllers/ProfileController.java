package ua.nure.tikhomirova.controllers;

import java.net.UnknownHostException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.nure.tikhomirova.dao.MongoDBManager;

@Controller
public class ProfileController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies)
			if (c.getName().equals("id"))
				if (c.getValue().equals(""))
					return "home";
				else {
					try {
						model.addAttribute("routes", MongoDBManager
								.getInstance().getRoutes(c.getValue()));
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					return "profile";
				}
		return "home";
	}
}
