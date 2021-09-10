$(document).ready(function(){
	$.get({
		url: "rest/ManagerService/getManager",
		contentType: "application/json",
		complete: function(message){
			manager = JSON.parse(message.responseText);
			let dateOfBirth = new Date(manager.dateOfBirth);
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
			if(manager.gender == "male"){
				document.getElementById("gender").selectedIndex = 0;
			}
			else if(manager.gender == "female"){
				document.getElementById("gender").selectedIndex = 1;
			}
			else{
				document.getElementById("gender").selectedIndex = 2;
			}
			$("#name").val(manager.name);
			$("#surname").val(manager.surname);
			$("#username").val(manager.username);
			
			$("#confirm").click(function(e){
				e.preventDefault();
				if($("#name").val().length == 0 || $("#surname").val() == 0 || $("#username").val() == 0){
					alert("fill in all fields");
					return;
				}
				manager.name = $("#name").val();
				manager.surname = $("#surname").val();
				manager.username = $("#username").val();
				manager.dateOfBirth = $("#date").val();
				manager.gender = $("#gender").val();
				$.post({
					url: "rest/ManagerService/changeManager",
					contentType: "application/json",
					data: JSON.stringify(manager),
					complete: function(message){
						alert("Your profile has been updated");
						window.location = "manager.html";
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
					url: "rest/ManagerService/changePassword",
					contentType: "application/json",
					data: JSON.stringify(data),
					complete: function(message){
						alert(message.responseText)
						if(message.responseText == "Password changed!"){
							window.location = "manager.html";
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