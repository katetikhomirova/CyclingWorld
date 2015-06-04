<%@ include file="includes/header.jsp"%>

<link
	href="${pageContext.request.contextPath}/resources/css/homePage.css"
	rel="stylesheet">
</head>
<body>
	<div id="fb-root"></div>
	<script
		src="${pageContext.request.contextPath}/resources/js/facebook.js"></script>
	<div id="wrap">
		<%@ include file="includes/menu.jsp"%>
		<div class="container">
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="row" id="homeLoginForm">
						<div class="col-md-4"></div>
						<div class="col-md-4">
							<div class="well">
								<p>Welcome to Cycling World!</p>
								<p id="greetings">There you can create your cycle routes and
									go for a cycling with your friends. Enjoy!</p>
								<a onclick="login()"><img
									src="http://i.stack.imgur.com/pZzc4.png" id="logInButton"></a>
							</div>
						</div>
						<div class="col-md-4"></div>
					</div>
				</div>
				<div class="col-md-2"></div>
			</div>
		</div>
		<div id="push"></div>
	</div>
	<%@ include file="includes/footer.html"%>
	
</body>
</html>
