var map = null;
var polyLine;
var tmpPolyLine;
var markers = [];
var vmarkers = [];
var g = google.maps;
var distance;
// var coords =[];

function initMap(routeName) {
	var xhr = new XMLHttpRequest();
	var str = '/rest/getRoute/' + get_cookie("id") + '/' + routeName;
	xhr.open("GET", str, true);

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var responseRoute = JSON.parse(xhr.responseText);

			markers = [];
			vmarkers = [];
			var mapOptions = {
				zoom : 15,
				center : new g.LatLng(49.988, 36.233),
				mapTypeId : g.MapTypeId.ROADMAP,
				draggableCursor : 'auto',
				draggingCursor : 'move',
				disableDoubleClickZoom : true,
				disableDefaultUI : true
			};
			map = new g.Map(document.getElementById("mapcontainer"), mapOptions);

			coords = [];
			for ( var i in responseRoute.coords) {
				coords.push(new google.maps.LatLng(responseRoute.coords[i].lat,
						responseRoute.coords[i].lng));
			}
			var polyOptions = {
				path : coords,
				strokeColor : "#228B22",
				strokeOpacity : 0.8,
				strokeWeight : 4
			};
			polyLine = new g.Polyline(polyOptions);
			polyLine.setMap(map);
			map.panTo(polyLine.getPath().getAt(0));
		}
	};
	xhr.send(null);

};