window.onload = function() {
	xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
			var htmlStr = "";
			var routes = JSON.parse(xmlHttp.responseText);
			for ( var i in routes) {
				var coordsString = getCoordsString(routes[i].coords);
				htmlStr += "<hr><div class=\"row myRow\"><div class=\"col-md-6\"><h5>";
				htmlStr += routes[i].name;
				htmlStr += "</h5></div>";
				htmlStr += "<div class=\"col-md-2\"><h5>";
				htmlStr += routes[i].distance;
				htmlStr += "km</h5></div>";
				htmlStr += "<div class=\"col-md-2\"><button class=\"btn btn-large btn-primary\" onclick=\"initMap('";
				htmlStr += coordsString + "');\">Show</button>";
				htmlStr += "</div><div class=\"col-md-2\"><button class=\"btn btn-large btn-danger removeBtn\">";
				htmlStr += "<i class=\"glyphicon glyphicon-remove\"></i></button></div></div>";

			}
			document.getElementById("routeList").innerHTML = htmlStr;

		}

	}
	xmlHttp.open("GET", 'rest/getPolyLines/' + get_cookie("id"), true);
	xmlHttp.setRequestHeader("Accept", "application/json");
	xmlHttp.send(null);
	alert(htmlStr);
}

function getCoordsString(arr) {
	var str = "";
	for ( var i in arr) {
		str += arr[i].lat + "," + arr[i].lng + ";";
	}
	return str;
};
