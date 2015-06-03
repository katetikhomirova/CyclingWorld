package ua.nure.tikhomirova.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session) {
		session.setAttribute("userID", null);
		return "home";
	}

	@RequestMapping(value = "/setId", method = RequestMethod.GET)
	public String profile(HttpServletRequest request, HttpSession session) {
		session.setAttribute("userID", request.getParameter("id"));
		return null;
	}

}
