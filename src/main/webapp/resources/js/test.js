$(document)
		.ready(
				function() {
					$('#superButton')
							.bind(
									'click',
									{
										name : "Donald Duck",
										city : "Duckburg"
									},
									function() {
										$
												.post(
														window.location.href,
														function(success) {
															alert(success);
														});
									});
				});