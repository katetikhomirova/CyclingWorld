<%@ include file="includes/header.jsp"%>

<link href="/resources/css/homePage.css" rel="stylesheet">
<link href="/resources/css/profile.css" rel="stylesheet">
<link href="/resources/css/friends.css" rel="stylesheet">
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script src="/resources/js/mapShow.js"></script>
</head>
<body>
	<div id="wrap">
		<%@ include file="includes/menu.jsp"%>
		<div class="container">
			<div class="row profile">
				<%@ include file="includes/leftProfileWindow.jsp"%>
				<div class="col-md-9">
					<div class="profile-content">
						<div class="row myRow">
							<div class="col-md-6">
								<h3>Friends:</h3>
							</div>
							<div class="col-md-6">
								<input type="button" class="btn btn-large btn-primary"
									onclick="inviteFriend()"
									value="Invite friend to Cycling World!" />
							</div>
						</div>
						<div id="friendsList"></div>
					</div>
				</div>
			</div>
		</div>
		<div id="push"></div>
	</div>
	<div id="fb-root"></div>
	<%@ include file="includes/footer.html"%>
	<script type="text/javascript" src="resources/js/leftProfileWindow.js"></script>
	<script type="text/javascript" src="resources/js/friends.js"></script>
</body>
</html>