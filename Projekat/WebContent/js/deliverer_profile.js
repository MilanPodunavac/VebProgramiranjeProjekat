$(document).ready(function(){
	$.get({
		url: "rest/DelivererService/getDeliverer",
		contentType: "application/json",
		complete: function(message){
			deliverer = JSON.parse(message.responseText);
			let dateOfBirth = new Date(deliverer.dateOfBirth);
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
			if(deliverer.gender == "male"){
				document.getElementById("gender").selectedIndex = 0;
			}
			else if(deliverer.gender == "female"){
				document.getElementById("gender").selectedIndex = 1;
			}
			else{
				document.getElementById("gender").selectedIndex = 2;
			}
			$("#name").val(deliverer.name);
			$("#surname").val(deliverer.surname);
			$("#username").val(deliverer.username);
			
			$("#confirm").click(function(e){
				e.preventDefault();
				if($("#name").val().length == 0 || $("#surname").val() == 0 || $("#username").val() == 0){
					alert("fill in all fields");
					return;
				}
				deliverer.name = $("#name").val();
				deliverer.surname = $("#surname").val();
				deliverer.username = $("#username").val();
				deliverer.dateOfBirth = $("#date").val();
				deliverer.gender = $("#gender").val();
				$.post({
					url: "rest/DelivererService/changeDeliverer",
					contentType: "application/json",
					data: JSON.stringify(deliverer),
					complete: function(message){
						alert("Your profile has been updated");
						window.location = "deliverer.html";
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
					url: "rest/DelivererService/changePassword",
					contentType: "application/json",
					data: JSON.stringify(data),
					complete: function(message){
						alert(message.responseText)
						if(message.responseText == "Password changed!"){
							window.location = "deliverer.html";
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