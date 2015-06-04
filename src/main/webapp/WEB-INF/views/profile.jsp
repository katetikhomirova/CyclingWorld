<%@ include file="includes/header.jsp"%>

<link href="/resources/css/homePage.css" rel="stylesheet">
<link href="/resources/css/profile.css" rel="stylesheet">
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
					<div class="profile-content">There are no routes yet...</div>
				</div>
			</div>
		</div>
		<div id="push"></div>
	</div>
	<%@ include file="includes/footer.html"%>
	<script type="text/javascript" src="resources/js/profile.js"></script>
</body>
</html>