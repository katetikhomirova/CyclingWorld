var map = null;
var polyLine;
var tmpPolyLine;
var markers = [];
var vmarkers = [];
var g = google.maps;
var distance;

var initMap = function(mapHolder) {
	markers = [];
	vmarkers = [];
	var mapOptions = {
		zoom : 14,
		center : new g.LatLng(49.988, 36.233),
		mapTypeId : g.MapTypeId.ROADMAP,
		draggableCursor : 'auto',
		draggingCursor : 'move',
		disableDoubleClickZoom : true
	};
	map = new g.Map(document.getElementById(mapHolder), mapOptions);
	g.event.addListener(map, "click", mapLeftClick);
	mapHolder = null;
	mapOptions = null;
};

var initPolyline = function() {
	var polyOptions = {
		strokeColor : "#228B22",
		strokeOpacity : 0.8,
		strokeWeight : 4
	};
	var tmpPolyOptions = {
		strokeColor : "#228B22",
		strokeOpacity : 0.4,
		strokeWeight : 4
	};
	polyLine = new g.Polyline(polyOptions);
	polyLine.setMap(map);
	tmpPolyLine = new g.Polyline(tmpPolyOptions);
	tmpPolyLine.setMap(map);
};

var mapLeftClick = function(event) {
	if (event.latLng) {
		var marker = createMarker(event.latLng);
		markers.push(marker);
		if (markers.length != 1) {
			var vmarker = createVMarker(event.latLng);
			vmarkers.push(vmarker);
			vmarker = null;
		}
		var path = polyLine.getPath();
		path.push(event.latLng);
		marker = null;
	}
	event = null;
};

var createMarker = function(point) {
	var imageNormal = new g.MarkerImage(
			"http://astrology.grimuar.info/images/kvadrat.png", new g.Size(11,
					11), new g.Point(0, 0), new g.Point(6, 6));
	var imageHover = new g.MarkerImage(
			"http://astrology.grimuar.info/images/kvadrat.png", new g.Size(11,
					11), new g.Point(0, 0), new g.Point(6, 6));
	var marker = new g.Marker({
		position : point,
		map : map,
		icon : imageNormal,
		draggable : true
	});
	g.event.addListener(marker, "mouseover", function() {
		marker.setIcon(imageHover);
	});
	g.event.addListener(marker, "mouseout", function() {
		marker.setIcon(imageNormal);
	});
	g.event.addListener(marker, "drag", function() {
		for (var m = 0; m < markers.length; m++) {
			if (markers[m] == marker) {
				polyLine.getPath().setAt(m, marker.getPosition());
				moveVMarker(m);
				break;
			}
		}
		m = null;
	});
	g.event.addListener(marker, "click", function() {
		for (var m = 0; m < markers.length; m++) {
			if (markers[m] == marker) {
				marker.setMap(null);
				markers.splice(m, 1);
				polyLine.getPath().removeAt(m);
				removeVMarkers(m);
				break;
			}
		}
		m = null;
	});
	return marker;
};

var createVMarker = function(point) {
	var prevpoint = markers[markers.length - 2].getPosition();
	var imageNormal = new g.MarkerImage(
			"http://astrology.grimuar.info/images/kvadrat.png", new g.Size(11,
					11), new g.Point(0, 0), new g.Point(6, 6));
	var imageHover = new g.MarkerImage(
			"http://astrology.grimuar.info/images/kvadrat.png", new g.Size(11,
					11), new g.Point(0, 0), new g.Point(6, 6));
	var marker = new g.Marker({
		position : new g.LatLng(point.lat()
				- (0.5 * (point.lat() - prevpoint.lat())), point.lng()
				- (0.5 * (point.lng() - prevpoint.lng()))),
		map : map,
		icon : imageNormal,
		draggable : true
	});
	g.event.addListener(marker, "mouseover", function() {
		marker.setIcon(imageHover);
	});
	g.event.addListener(marker, "mouseout", function() {
		marker.setIcon(imageNormal);
	});
	g.event.addListener(marker, "dragstart", function() {
		for (var m = 0; m < vmarkers.length; m++) {
			if (vmarkers[m] == marker) {
				var tmpPath = tmpPolyLine.getPath();
				tmpPath.push(markers[m].getPosition());
				tmpPath.push(vmarkers[m].getPosition());
				tmpPath.push(markers[m + 1].getPosition());
				break;
			}
		}
		m = null;
	});
	g.event.addListener(marker, "drag", function() {
		for (var m = 0; m < vmarkers.length; m++) {
			if (vmarkers[m] == marker) {
				tmpPolyLine.getPath().setAt(1, marker.getPosition());
				break;
			}
		}
		m = null;
	});
	g.event.addListener(marker, "dragend", function() {
		for (var m = 0; m < vmarkers.length; m++) {
			if (vmarkers[m] == marker) {
				var newpos = marker.getPosition();
				var startMarkerPos = markers[m].getPosition();
				var firstVPos = new g.LatLng(newpos.lat()
						- (0.5 * (newpos.lat() - startMarkerPos.lat())), newpos
						.lng()
						- (0.5 * (newpos.lng() - startMarkerPos.lng())));
				var endMarkerPos = markers[m + 1].getPosition();
				var secondVPos = new g.LatLng(newpos.lat()
						- (0.5 * (newpos.lat() - endMarkerPos.lat())), newpos
						.lng()
						- (0.5 * (newpos.lng() - endMarkerPos.lng())));
				var newVMarker = createVMarker(secondVPos);
				newVMarker.setPosition(secondVPos);// apply the correct
				// position to the vmarker
				var newMarker = createMarker(newpos);
				markers.splice(m + 1, 0, newMarker);
				polyLine.getPath().insertAt(m + 1, newpos);
				marker.setPosition(firstVPos);
				vmarkers.splice(m + 1, 0, newVMarker);
				tmpPolyLine.getPath().removeAt(2);
				tmpPolyLine.getPath().removeAt(1);
				tmpPolyLine.getPath().removeAt(0);
				newpos = null;
				startMarkerPos = null;
				firstVPos = null;
				endMarkerPos = null;
				secondVPos = null;
				newVMarker = null;
				newMarker = null;
				break;
			}
		}
	});
	return marker;
};

var moveVMarker = function(index) {
	var newpos = markers[index].getPosition();
	if (index != 0) {
		var prevpos = markers[index - 1].getPosition();
		vmarkers[index - 1].setPosition(new g.LatLng(newpos.lat()
				- (0.5 * (newpos.lat() - prevpos.lat())), newpos.lng()
				- (0.5 * (newpos.lng() - prevpos.lng()))));
		prevpos = null;
	}
	if (index != markers.length - 1) {
		var nextpos = markers[index + 1].getPosition();
		vmarkers[index].setPosition(new g.LatLng(newpos.lat()
				- (0.5 * (newpos.lat() - nextpos.lat())), newpos.lng()
				- (0.5 * (newpos.lng() - nextpos.lng()))));
		nextpos = null;
	}
	newpos = null;
	index = null;

};

var removeVMarkers = function(index) {
	if (markers.length > 0) {// при клике на маркере он удаляется
		if (index != markers.length) {
			vmarkers[index].setMap(null);
			vmarkers.splice(index, 1);
		} else {
			vmarkers[index - 1].setMap(null);
			vmarkers.splice(index - 1, 1);
		}
	}
	if (index != 0 && index != markers.length) {
		var prevpos = markers[index - 1].getPosition();
		var newpos = markers[index].getPosition();
		vmarkers[index - 1].setPosition(new g.LatLng(newpos.lat()
				- (0.5 * (newpos.lat() - prevpos.lat())), newpos.lng()
				- (0.5 * (newpos.lng() - prevpos.lng()))));
		prevpos = null;
		newpos = null;
	}
	index = null;
};

window.onload = function() {
	initMap('mapcontainer');
	initPolyline();
};

// Функция для определения длины полилинии
function distance() {

	var dist = 0;

	if (markers.length > 0) {
		for (var im = 0; im < markers.length - 1; im++) {
			var marpos1 = markers[im].getPosition();
			var marpos2 = markers[im + 1].getPosition();

			var R = 6371000; // km (коэффициент для определения расстояния
			// между двумя точками в километрах)
			var dLat = (marpos2.lat() - marpos1.lat()) * Math.PI / 180;
			var dLon = (marpos2.lng() - marpos1.lng()) * Math.PI / 180;
			var a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
					+ Math.cos(marpos1.lat() * Math.PI / 180)
					* Math.cos(marpos2.lat() * Math.PI / 180)
					* Math.sin(dLon / 2) * Math.sin(dLon / 2);
			var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			var d = R * c;
			dist = dist + d;

		}
		distance = Math.round(dist / 10) / 100;
	}
}

function get_cookie(cookie_name) {
	var results = document.cookie.match('(^|;) ?' + cookie_name
			+ '=([^;]*)(;|$)');

	if (results)
		return (unescape(results[2]));
	else
		return null;
};

function savePolyline() {
	distance();
	console.log(distance);
	var isPublic;
	if (document.getElementById('isPublic').checked)
		isPublic = "true";
	else
		isPublic = "false";
	var url = "/savePolyLine?line=" + get_cookie("id") + ","
			+ document.getElementById('name').value + "," + distance + ","
			+ isPublic;
	for (var i = 0; i < polyLine.getPath().getLength(); i++) {
		url = url + "," + polyLine.getPath().getAt(i).toUrlValue();
	}
	xmlHttp = new XMLHttpRequest();

	xmlHttp.open("GET", url, true);
	xmlHttp.send(null);
	window.location = "/profile";
}