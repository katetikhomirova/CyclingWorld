function get_cookie(cookie_name) {
	var results = document.cookie.match('(^|;) ?' + cookie_name
			+ '=([^;]*)(;|$)');

	if (results)
		return (decodeURIComponent(results[2]).split('+').join(' '));
	else
		return null;
};
var im = document.getElementById("userImage")
		.setAttribute(
				"src",
				"http://graph.facebook.com/" + get_cookie("id")
						+ "/picture?type=large");

changeList = function(page) {
	if (page == "profile") {
		$('#profileLink').addClass('active');
		$('#friendsLink').removeClass('active');
		$('#addNewRouteLink').removeClass('active');
	}
	if (page == "friends") {
		$('#friendsLink').addClass('active');
		$('#profileLink').removeClass('active');
		$('#addNewRouteLink').removeClass('active');
	}

	if (page == "addNewRoute") {
		$('#addNewRouteLink').addClass('active');
		$('#friendsLink').removeClass('active');
		$('#profileLink').removeClass('active');
	}
}

changeUserName = function() {
	document.getElementById("userName").innerHTML = "<h4>" + get_cookie("name")
			+ " " + get_cookie("surname") + "</h4>";
}
