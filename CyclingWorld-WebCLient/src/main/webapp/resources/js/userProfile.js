window.onload = function() {
	changeList("friends");
	changeUserName();
	xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var htmlStr = "";
			var routes = JSON.parse(xmlHttp.responseText);
			if (routes.length > 0) {
				for ( var i in routes) {
					htmlStr += "<hr><div class=\"row myRow\"><div class=\"col-md-7 col-sm-6 col-xs-12\"><h4>";
					htmlStr += routes[i].name;
					htmlStr += "</h4></div>";
					htmlStr += "<div class=\"col-md-3 col-sm-3 col-xs-12\"><h5>";
					htmlStr += routes[i].distance;
					htmlStr += "km</h5></div>";
					htmlStr += "<div class=\"col-md-2 col-sm-3 col-xs-12\"><button class=\"btn btn-large btn-primary\" onclick=\"initMap('";
					htmlStr += get_cookie("requestedId") + "','"
							+ routes[i].name + "');\">Show</button>";
					htmlStr += "</div></div>";

				}
			} else
				htmlStr = "<hr><div class=\"row myRow\">There are no routes yet...</div>";
			document.getElementById("routeList").innerHTML = htmlStr;

		}

	}
	xmlHttp.open("GET",
			'http://cyclingworld-service.cfapps.io/rest/getPublicRoutes/'
					+ get_cookie("requestedId"), true);
	xmlHttp.setRequestHeader("Accept", "application/json");
	xmlHttp.send(null);

	var friendInfoStr = "<div class=\"row myRow\"><div class=\"col-md-4 col-sm-5 col-xs-6\">";
	friendInfoStr += "<div class = \"friendsPic\"><img src=\"https://graph.facebook.com/"
			+ get_cookie("requestedId")
			+ "/picture?type=large\" class=\"img-responsive\"></div>";
	friendInfoStr += "</div><div class=\"col-md-8 col-sm-7 col-xs-6\"><div class=\"row\"><h4>"
			+ get_cookie("requestedName").replace(/"/g, '');
	+"</h4></div></div>";
	document.getElementById("userInfo").innerHTML = friendInfoStr;
}