<nav class="navbar navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Cycling World</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<%
				if (request.getCookies() != null)
					for (Cookie c : request.getCookies())
						if (c.getName().equals("id") && !c.getValue().equals("")) {
			%>
			<!-- <ul class="nav navbar-nav">
				<li class="active"><a href="/profile">Profile</a></li>
			</ul> -->
			<p class="navbar-text navbar-right">
				Welcome, ${cookie.name.value}! <a href="#" onclick="logout()">logout</a>
			</p>
			<%
				}
				//}
			%>
		</div>

	</div>
</nav>