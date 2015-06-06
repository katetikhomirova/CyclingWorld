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

changeList = function() {
	if (window.location.pathname == "/profile") {
		alert("Profile");
		$('#profileLink').addClass('active');
		$('#friendsLink').removeClass('active');
		$('#addNewRouteLink').removeClass('active');
	}
	if (window.location.pathname == "/friends") {
		alert("Friends");
		alert($('#friendsLink').innerHTML);
		$('#friendsLink').addClass('active');
		$('#profileLink').removeClass('active');
		$('#addNewRouteLink').removeClass('active');
	}

	if (window.location.pathname == "/addNewRoute") {
		alert("Add new route");
		$('#addNewRouteLink').addClass('active');
		$('#friendsLink').removeClass('active');
		$('#profileLink').removeClass('active');
	}
}