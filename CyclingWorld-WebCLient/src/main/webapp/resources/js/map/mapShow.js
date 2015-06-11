var map = null;
var polyLine;
var tmpPolyLine;
var markers = [];
var vmarkers = [];
var g = google.maps;
var distance;

function initMap(id, coordsStr) {
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

	coords = coordsStr.split(";");
	coordsArr = [];
	for (var i = 0; i < coords.length - 1; i++) {
		point = coords[i].split(",");
		coordsArr.push(new google.maps.LatLng(point[0], point[1]));
	}
	var polyOptions = {
		path : coordsArr,
		strokeColor : "#228B22",
		strokeOpacity : 0.8,
		strokeWeight : 4
	};
	polyLine = new g.Polyline(polyOptions);
	polyLine.setMap(map);
	map.panTo(polyLine.getPath().getAt(0));
};