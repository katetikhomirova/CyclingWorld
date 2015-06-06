package ua.nure.tikhomirova.controllers;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AddNewRouteController {

	@RequestMapping(value = "/addNewRoute", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies)
				if (c.getName().equals("id"))
					if (c.getValue().equals(""))
						return "home";
					else {
						return "addNewRoute";
					}
		}
		return "home";
	}
}
