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
				<%@ include file="includes/leftProfileWindow.jsp"%>
				<div class="col-md-9">
					<div class="profile-content">
						<div class="col-md-12">
							<div class="row myRow">
								<div class="col-md-10">
									<h4>My routes:</h4>
								</div>
								<div class="col-md-2">
									<input type="button" class="btn btn-large btn-primary"
										onclick="location.href='/addNewRoute'" value="Add route" />
								</div>
							</div>
							<hr>
							<div class="row myRow">There are no routes yet...</div>
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