function get_cookie(cookie_name) {
	var results = document.cookie.match('(^|;) ?' + cookie_name
			+ '=([^;]*)(;|$)');

	if (results)
		return (unescape(results[2]));
	else
		return null;
};
var im = document.getElementById("userImage")
		.setAttribute(
				"src",
				"http://graph.facebook.com/" + get_cookie("id")
						+ "/picture?type=large");