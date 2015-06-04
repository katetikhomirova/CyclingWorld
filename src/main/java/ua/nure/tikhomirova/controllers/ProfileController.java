package ua.nure.tikhomirova.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie c : cookies)
			if (c.getName().equals("id"))
				if (c.getValue().equals(""))
					return "home";
				else
					return "profile";
		return "home";
	}
}
