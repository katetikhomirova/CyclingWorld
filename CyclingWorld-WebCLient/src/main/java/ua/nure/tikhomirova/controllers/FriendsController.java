package ua.nure.tikhomirova.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class FriendsController {
	
	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies)
				if (c.getName().equals("id"))
					if (c.getValue().equals(""))
						return "home";
					else {
						return "friends";
					}
		}
		return "home";
	}
}
