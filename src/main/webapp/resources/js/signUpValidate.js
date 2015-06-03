$(document).ready(
		function() {

			$('#signUpForm').validate(
					{
						rules : {
							firstName : {
								minlength : 2,
								required : true
							},
							login : {
								minlength : 5,
								required : true
							},
							lastName : {
								minlength : 2,
								required : true
							},
							email : {
								required : true,
								email : true
							},
							password : {
								required : true,
								minlength : 6,
								maxlength : 20
							},
							telephone : {
								number : true
							},
							repeatPassword : {
								required : true,
								equalTo : "#password"
							},
							comment : {
								minlength : 2
							},
						},
						highlight : function(element) {
							$(element).closest('.form-group').removeClass(
									'has-success').addClass('has-error');
						},
						success : function(element) {
							element.text('OK!').addClass('valid').closest(
									'.form-group').removeClass('has-error')
									.addClass('has-success');
						}
					});
			
		});