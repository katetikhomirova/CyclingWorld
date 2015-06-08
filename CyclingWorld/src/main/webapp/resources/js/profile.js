window.onload = function() {
	changeList();
	xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var htmlStr = "";
			var routes = JSON.parse(xmlHttp.responseText);
			if (routes.length > 0) {
				for ( var i in routes) {
					htmlStr += "<hr><div class=\"row myRow\"><div class=\"col-md-6 col-sm-8 col-xs-12\"><h4>";
					htmlStr += routes[i].name;
					htmlStr += "</h4></div>";
					htmlStr += "<div class=\"col-md-2 col-sm-4 col-xs-12\"><h5>";
					htmlStr += routes[i].distance;
					htmlStr += "km</h5></div>";
					htmlStr += "<div class=\"col-md-2 col-sm-6 col-xs-6\"><button class=\"btn btn-large btn-primary\" onclick=\"initMap('";
					htmlStr += routes[i].name + "');\">Show</button>";
					htmlStr += "</div><div class=\"col-md-2 col-sm-6 col-xs-6\"><button class=\"btn btn-large btn-danger removeBtn\" onclick=\"removeRoute('";
					htmlStr += routes[i].name + "');\">";
					htmlStr += "<i class=\"glyphicon glyphicon-remove\"></i></button></div></div>";

				}
			} else
				htmlStr = "<hr><div class=\"row myRow\">There are no routes yet...</div>";
			document.getElementById("routeList").innerHTML = htmlStr;

		}

	}
	xmlHttp.open("GET", 'rest/getPolyLines/' + get_cookie("id"), true);
	xmlHttp.setRequestHeader("Accept", "application/json");
	xmlHttp.send(null);
}

function removeRoute(name) {

	if (confirm('Are you sure?')) {
		var xhr = new XMLHttpRequest();
		var str = '/rest/removeRoute/' + get_cookie("id") + '/' + name;
		xhr.open("GET", str, true);

		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				alert("Succesfully removed!");
				window.location = "/profile";
			}
		}
		xhr.send(null);
	}
}
