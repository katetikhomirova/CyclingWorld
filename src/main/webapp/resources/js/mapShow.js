var map = null;
var polyLine;
var tmpPolyLine;
var markers = [];
var vmarkers = [];
var g = google.maps;
var distance;
//var coords =[];

function initMap(route) {
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

	arr = route.split(';')
	coords = [];
	for (var i = 0; i < arr.length; i++) {
		var p = arr[i].split(',');
		coords.push(new google.maps.LatLng(p[0], p[1]));
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
};