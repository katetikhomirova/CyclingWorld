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
				var arr = response.name.split("  ");
				document.cookie = "name=" + arr[0];
				if (arr[1] != null)
					document.cookie = "surname=" + arr[1];
				else
					document.cookie = "surname=";
				if (window.location.pathname == '/')
					window.location = "/profile";
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
				document.cookie = "id=" + response.id;
				var arr = response.name.split("  ");
				document.cookie = "name=" + arr[0];
				if (arr[1] != null)
					document.cookie = "surname=" + arr[1];
				else
					document.cookie = "surname=";
				window.location = "/profile";
			});
			console.log("u r logged in");
		} else {
			console.log("login error");
		}

	});
}

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
	js.src = "//connect.facebook.net/ru_RU/sdk.js#xfbml=1&appId=1544982615746209&version=v2.0";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));