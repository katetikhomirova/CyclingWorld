package ua.nure.tikhomirova.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserProfileController {

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/profile/{id}/{name}", method = RequestMethod.GET)
	public String profile(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String id,
			@PathVariable String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies)
				if (c.getName().equals("id"))
					if (c.getValue().equals(""))
						return "home";
					else {
						boolean idWasFounded = false;
						boolean nameWasFounded = false;
						for (Cookie cookie : cookies) {
							if (cookie.getName().equals("requestedId")) {
								idWasFounded = true;
								cookie.setValue(id);
							}
							if (cookie.getName().equals("requestedName")) {
								nameWasFounded = true;
								cookie.setValue(URLEncoder.encode(name));
							}
						}
						if (!idWasFounded) {
							Cookie requestedId = new Cookie("requestedId", id);
							response.addCookie(requestedId);
						}
						if (!nameWasFounded) {
							Cookie requestedName = new Cookie("requestedName",
									URLEncoder.encode(name));
							response.addCookie(requestedName);
						}
						return "userProfile";
					}
		}
		return "home";
	}
}