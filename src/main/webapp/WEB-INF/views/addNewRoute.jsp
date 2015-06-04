<%@ include file="includes/header.jsp"%>

<link href="/resources/css/homePage.css" rel="stylesheet">
<link href="/resources/css/profile.css" rel="stylesheet">
<link href="/resources/css/addNewRoute.css" rel="stylesheet">
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="/resources/js/map.js"></script>
</head>
<body>
	<div id="fb-root"></div>
	<script src="/resources/js/facebook.js"></script>
	<div id="wrap">
		<%@ include file="includes/menu.jsp"%>
		<div class="container">
			<div class="row profile">
				<%@ include file="includes/leftProfileWindow.jsp"%>
				<div class="col-md-9">
					<div class="profile-content">
						<div class="row">
							<div class="row myRow">
								<div class="col-md-12">
									<h3>Create new route</h3>
								</div>
							</div>
							<div class="row myRow">
								<div class="col-md-2">
									<h5>Distance:</h5>
								</div>
								<div class="col-md-1">
									<h5 id="distance">0 km</h5>
								</div>
								<div class="col-md-2">
									<div id="nameLbl">
										<h5>Name:</h5>
									</div>
								</div>
								<div class="col-md-3">
									<div id="nameInput">
										<input id="name" type="text">
									</div>
								</div>
								<div class="col-md-1">
									<div>
										<h5>Public:</h5>
									</div>
								</div>
								<div class="col-md-1">
									<input type="checkbox" id="isPublic">
								</div>
								<div class="col-md-2">
									<input type="button" class="btn btn-large btn-primary"
										onclick="savePolyline();" value="Save" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
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
	<script type="text/javascript" src="resources/js/profile.js"></script>
</body>
</html>