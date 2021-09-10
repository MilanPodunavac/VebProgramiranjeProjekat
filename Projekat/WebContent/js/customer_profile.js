$(document).ready(function(){
	$.get({
		url: "rest/CustomerService/getCustomer",
		contentType: "application/json",
		complete: function(message){
			customer = JSON.parse(message.responseText);
			let dateOfBirth = new Date(customer.dateOfBirth);
			let date = dateOfBirth.getYear() + 1900;
			if((dateOfBirth.getMonth() + 1) < 10){
				date += "-0" +  (dateOfBirth.getMonth() + 1);
			}
			else{
				date += "-" +  (dateOfBirth.getMonth() + 1);
			}
			if(dateOfBirth.getDate() < 10){
				date += "-0" +  dateOfBirth.getDate();
			}else
			{
				date += "-" +  dateOfBirth.getDate();
			}
			$("#date").val(date);
			if(customer.gender == "male"){
				document.getElementById("gender").selectedIndex = 0;
			}
			else if(customer.gender == "female"){
				document.getElementById("gender").selectedIndex = 1;
			}
			else{
				document.getElementById("gender").selectedIndex = 2;
			}
			$("#name").val(customer.name);
			$("#surname").val(customer.surname);
			$("#username").val(customer.username);
			$("#type").val(customer.customerType.name);
			$("#points").val(customer.points);
			
			$("#confirm").click(function(e){
				e.preventDefault();
				if($("#name").val().length == 0 || $("#surname").val() == 0 || $("#username").val() == 0){
					alert("fill in all fields");
					return;
				}
				customer.name = $("#name").val();
				customer.surname = $("#surname").val();
				customer.username = $("#username").val();
				customer.dateOfBirth = $("#date").val();
				customer.gender = $("#gender").val();
				$.post({
					url: "rest/CustomerService/changeCustomer",
					contentType: "application/json",
					data: JSON.stringify(customer),
					complete: function(message){
						alert("Your profile has been updated");
						window.location = "customer.html";
					}
				})
			})
			$("#confirmPasswordChange").click(function(e){
				e.preventDefault();
				if($("#newPassword").val() != $("#confirmNewPassword").val()){
					alert("new password and confirm new password are invalid");
					return;
				}
				if($("#newPassword").val().length < 8){
					alert("new password must be longer than 7");
					return;
				}
				let data = {oldPassword : $("#currentPassword").val(), newPassword : $("#newPassword").val()}
				$.post({
					url: "rest/CustomerService/changePassword",
					contentType: "application/json",
					data: JSON.stringify(data),
					complete: function(message){
						alert(message.responseText)
						if(message.responseText == "Password changed!"){
							window.location = "customer.html";
						}else{
							$("#currentPassword").val("");
							$("#newPassword").val("");
						}
					}
				})
			})
		}
	})
})