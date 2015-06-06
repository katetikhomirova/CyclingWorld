var ids = [];

window.fbAsyncInit = function() {
	FB.init({
		appId : '1436561916645343',
		status : true,
		xfbml : true,
		oauth : true,
		version : 'v2.0'
	});

	FB
			.getLoginStatus(function(response) {
				if (response.status === 'connected') {
					if (response.status === 'connected') {
						FB
								.api(
										'/me/friends',
										function(response) {
											var htmlStr = "";
											for ( var i in response.data) {
												htmlStr += "<hr><div class=\"row myRow\"><div class=\"col-md-2\">";
												htmlStr += "<div class = \"friendsPic\"><img src=\"https://graph.facebook.com/"
														+ response.data[i].id
														+ "/picture?type=normal\" class=\"img-responsive\"></div>";
												htmlStr += "</div><div class=\"col-md-10\"><div class=\"row\"><a><h4>"
														+ response.data[i].name
														+ "</h4></a></div><div class=\"row\" id=\""
														+ response.data[i].id
														+ "\"></div></div></div>";
												ids.push(response.data[i].id);
											}
											document
													.getElementById("friendsList").innerHTML = htmlStr;
											for ( var i in ids) {
												xhr = new XMLHttpRequest();
												var str = '/rest/getRouteCount/'
														+ ids[i];
												xhr.open("GET", str, true);

												xhr.onreadystatechange = function() {
													if (xhr.readyState == 4
															&& xhr.status == 200) {
														var count = JSON
																.parse(xhr.responseText)
														var str = "";
														if (count == 1) {
															str = "<h5> Has 1 public route.</h5>";
														}
														if (count == 0) {
															str = "<h5>Hasn't public routes.</h5>";
														}
														if (count > 1) {
															str = "<h5> Has "
																	+ count
																	+ " public routes.</h5>";
														}
														document
																.getElementById(""
																		+ ids[i]).innerHTML = str;
													}
												}
												xhr.send(null);
											}
										});
						/*
						 * FB.api('/me/taggable_friends', function(response) { //
						 * makeFriendsView(); var next = response.paging.next;
						 * while (next){ xmlHttp = new XMLHttpRequest();
						 * xmlHttp.open("GET", next, true);
						 * xmlHttp.onreadystatechange = function() { if
						 * (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
						 * var friendsList = JSON.parse(xmlHttp.responseText);
						 * next = friendsList.paging.next; } }
						 * xmlHttp.send(null); } });
						 */
					}
				}
			});
};

function logout() {
	FB.logout(function(response) {
		var Url = "/logout";
		xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", Url, true);
		xmlHttp.send(null);
		document.cookie = "id=";
		document.cookie = "name=";
		document.cookie = "surname=";
		window.location = "/";
	})
}
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/ru_RU/sdk.js#xfbml=1&appId=1436561916645343&version=v2.0";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

window.onload = function() {
	changeList();
}

function inviteFriend() {

}