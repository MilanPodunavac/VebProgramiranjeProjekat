$(document).ready(function(){
	$.get({
		url: "rest/AdministratorService/getAdmin",
		contentType: "application/json",
		complete: function(message){
			administrator = JSON.parse(message.responseText);
			let dateOfBirth = new Date(administrator.dateOfBirth);
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
			if(administrator.gender == "male"){
				document.getElementById("gender").selectedIndex = 0;
			}
			else if(administrator.gender == "female"){
				document.getElementById("gender").selectedIndex = 1;
			}
			else{
				document.getElementById("gender").selectedIndex = 2;
			}
			$("#name").val(administrator.name);
			$("#surname").val(administrator.surname);
			$("#username").val(administrator.username);
			
			$("#confirm").click(function(e){
				e.preventDefault();
				if($("#name").val().length == 0 || $("#surname").val() == 0 || $("#username").val() == 0){
					alert("fill in all fields");
					return;
				}
				administrator.name = $("#name").val();
				administrator.surname = $("#surname").val();
				administrator.username = $("#username").val();
				administrator.dateOfBirth = $("#date").val();
				administrator.gender = $("#gender").val();
				$.post({
					url: "rest/AdministratorService/changeAdmin",
					contentType: "application/json",
					data: JSON.stringify(administrator),
					complete: function(message){
						alert("Your profile has been updated");
						window.location = "administrator.html";
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
					url: "rest/AdministratorService/changePassword",
					contentType: "application/json",
					data: JSON.stringify(data),
					complete: function(message){
						alert(message.responseText)
						if(message.responseText == "Password changed!"){
							window.location = "administrator.html";
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