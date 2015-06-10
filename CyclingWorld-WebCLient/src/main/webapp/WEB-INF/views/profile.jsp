<%@ include file="includes/header.jsp"%>

<link href="http://cyclingworld.cfapps.io/resources/css/homePage.css" rel="stylesheet">
<link href="http://cyclingworld.cfapps.io/resources/css/profile.css" rel="stylesheet">
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="http://cyclingworld.cfapps.io/resources/js/map/mapShow.js"></script>
</head>
<body>
	<div id="fb-root"></div>
	<script src="http://cyclingworld.cfapps.io/resources/js/facebook.js"></script>
	<div id="wrap">
		<%@ include file="includes/menu.jsp"%>
		<div class="container">
			<div class="row profile">
				<%@ include file="includes/leftProfileWindow.jsp"%>
				<div class="col-md-9">
					<div class="profile-content">
						<!-- <div class="col-md-12">  -->
						<div class="row myRow">
							<div class="col-md-6 col-sm-6 col-xs-6">
								<h4>My routes:</h4>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6">
								<input type="button" class="btn btn-large btn-primary"
									onclick="location.href='/addNewRoute'" value="Add route"
									id="addRouteBtn" />
							</div>
						</div>
						<div class="row myRow">
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div id="routeList"></div>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div id="mapcontainer"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="push"></div>
	</div>
	<%@ include file="includes/footer.html"%>
	<script type="text/javascript" src="http://cyclingworld.cfapps.io/resources/js/leftProfileWindow.js"></script>
	<script type="text/javascript" src="http://cyclingworld.cfapps.io/resources/js/profile.js"></script>
</body>
</html>