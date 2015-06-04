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
		<div class="container profile">
			<div class="container profilePanel">
				<div class="col-md-1">
					<img alt="User Pic" src="${cookie.picture.value}"
						class="profileImg">
				</div>
				<div class="col-md-3">
					<h3>${cookie.name.value}</h3>
				</div>
			</div>
		</div>
		<div id="push"></div>
	</div>
	<%@ include file="includes/footer.html"%>
</body>
</html>