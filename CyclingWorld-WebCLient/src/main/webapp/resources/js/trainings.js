window.onload = function() {
	changeList("trainings");
	changeUserName();
	xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var htmlStr = "";
			var trainings = JSON.parse(xmlHttp.responseText);
			if (trainings.length > 0) {
				for ( var i in trainings) {
					htmlStr += "<hr><div class=\"row myRow\"><div class=\"col-md-6 col-sm-8 col-xs-12\"><h5>";
					htmlStr += trainings[i].time;
					htmlStr += "</h5></div>";
					htmlStr += "<div class=\"col-md-3 col-sm-4 col-xs-12\"><h5>";
					htmlStr += trainings[i].distance;
					htmlStr += "km</h5></div>";
					htmlStr += "<div class=\"col-md-3 col-sm-12 col-xs-12\"><button class=\"btn btn-large btn-primary\" onclick=\"initMap('";
					htmlStr += get_cookie("id") + "','" + trainings[i].coordsString
							+ "');\">Show</button>";
					htmlStr += "</div></div>";

				}
			} else
				htmlStr = "<hr><div class=\"row myRow\">There are no training yet...</div>";
			document.getElementById("routeList").innerHTML = htmlStr;

		}

	}
	xmlHttp.open("GET",
			'http://cyclingworld-service.cfapps.io/rest/getTrainings/'
					+ get_cookie("id"), true);
	xmlHttp.setRequestHeader("Accept", "application/json");
	xmlHttp.send(null);
}