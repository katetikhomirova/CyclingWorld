<%@ include file="includes/header.jsp"%>

<link href="/resources/css/homePage.css" rel="stylesheet">
<link href="/resources/css/profile.css" rel="stylesheet">
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="/resources/js/mapShow.js"></script>
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
						<!-- <div class="col-md-12">  -->
						<div class="row myRow">
							<div class="col-md-6 col-sm-6 col-xs-6">
								<h3>My routes:</h3>
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
						<!-- <div class="row myRow">There are no routes yet...</div> -->

					</div>
				</div>
			</div>
		</div>
		<div id="push"></div>
	</div>
	<%@ include file="includes/footer.html"%>
	<script type="text/javascript" src="resources/js/leftProfileWindow.js"></script>
	<script type="text/javascript" src="resources/js/profile.js"></script>
</body>
</html>