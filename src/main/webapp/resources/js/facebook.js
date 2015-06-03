window.fbAsyncInit = function() {
	FB.init({
		appId : '1436561916645343',
		status : true,
		xfbml : true,
		oauth : true,
		version : 'v2.0'
	});

	FB.getLoginStatus(function(response) {
		if (response.status === 'connected') {
			FB.api('/me', {
				fields : 'name'
			}, function(response) {

				document.cookie = "id=" + response.id;
				document.cookie = "name=" + response.name;
				alert(document.cookie);
			});

		} else {
			var n = document.getElementById('logInButton');
			n.style.visibility = "visible";
		}
	});
};

function login() {
	FB.login(function(response) {
		if (response.authResponse) {
			FB.api('/me', {
				fields : 'name'
			}, function(response) {
				var Url = window.location.pathname + "setId?id=" + response.id;
				console.log(Url);
				console.log(response);
				xmlHttp = new XMLHttpRequest();

				xmlHttp.open("GET", Url, true);
				xmlHttp.send(null);
			});

			FB.api("/me/picture", {
				"redirect" : false,
				"height" : "200",
				"type" : "normal",
				"width" : "200"
			}, function(response) {
				if (response && !response.error) {
					var Url = window.location.pathname + "setId?id="
							+ response.id;
					console.log(Url);
					console.log(response);
					xmlHttp = new XMLHttpRequest();

					xmlHttp.open("GET", Url, true);
					xmlHttp.send(null);
					window.location = window.location.pathname + "profile";
				}
			});
			console.log("u r logged in");
		} else {
			console.log("login error");
		}

	});
}

function logout() {
	FB.logout(function(response) {
		var Url = "${pageContext.request.contextPath}/logout";
		xmlHttp = new XMLHttpRequest();
		xmlHttp.open("GET", Url, true);
		xmlHttp.send(null);
		window.location = "${pageContext.request.contextPath}/";
	})
}
(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/ru_RU/sdk.js#xfbml=1&appId=1544982615746209&version=v2.0";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));