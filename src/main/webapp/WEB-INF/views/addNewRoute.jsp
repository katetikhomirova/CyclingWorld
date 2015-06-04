<%@ include file="includes/header.jsp"%>

<link href="/resources/css/homePage.css" rel="stylesheet">
<link href="/resources/css/profile.css" rel="stylesheet">
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
				<div class="col-md-3">
					<div class="profile-sidebar">
						<!-- SIDEBAR USERPIC -->
						<div class="profile-userpic">
							<img src="" class="img-responsive" id="userImage" alt="">
						</div>
						<!-- END SIDEBAR USERPIC -->
						<!-- SIDEBAR USER TITLE -->
						<div class="profile-usertitle">
							<div class="profile-usertitle-name" id="userName">${cookie.name.value}
								${cookie.surname.value}</div>
						</div>
						<!-- END SIDEBAR USER TITLE -->
						<!-- SIDEBAR MENU -->
						<div class="profile-usermenu">
							<ul class="nav">
								<li class="active"><a href="#"> <i
										class="glyphicon glyphicon-home"></i> Home
								</a></li>
								<li><a href="#"> <i class="glyphicon glyphicon-user"></i>
										Friends
								</a></li>
								<li><a href="#"> <i class="glyphicon glyphicon-search"></i>
										Search route
								</a></li>
							</ul>
						</div>
						<!-- END MENU -->
					</div>
				</div>
				<div class="col-md-9">
					<div class="profile-content">
						<div class="row">
							<div class="col-md-5">
								<div class="row myRow">
									<div class="col-md-5">
										<h3>Create new route</h3>
									</div>
								</div>
								<div class="row myRow">
									<div class="col-md-3">
										<h5>Distance:</h5>
									</div>
									<div class="col-md-2" id="distance">0 km</div>
								</div>
								<div class="row myRow">
									<div class="col-md-3">Name:</div>
									<div class="col-md-2">
										<input id="name" type="text">
									</div>
								</div>
								<div class="row myRow">
									<div class="col-md-5">
										<input type="button" class="btn btn-large btn-primary"
											onclick="savePolyline();" value="Save" />
									</div>
								</div>
							</div>
							<div class="col-md-6 ">
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