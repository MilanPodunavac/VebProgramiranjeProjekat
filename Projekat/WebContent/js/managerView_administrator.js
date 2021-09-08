var manager;
$(document).ready(function(){
	var url = window.location.href;
	var parameters = url.split("?")[1];
	var username_parameter = parameters.split("&")[0];
	var username = username_parameter.split("=")[1];
	username = decodeURIComponent(username);
	$.get({
	    url: "rest/ManagerService/getManagerByUsername?username=" + username,
	    contentType: 'application/json',
	    complete: function(message) {
			manager = JSON.parse(message.responseText);
			let titleTag = document.getElementsByTagName("title")[0];
			titleTag.innerText = manager.username;
			
			let managerUsernameTd = document.getElementById("username_td");
			managerUsernameTd.appendChild(document.createTextNode("Username: " + manager.username));
			
			let managerNameTd = document.getElementById("name_td");
			managerNameTd.appendChild(document.createTextNode("Name: " + manager.name));
			
			let managerSurnameTd = document.getElementById("surname_td");
			managerSurnameTd.appendChild(document.createTextNode("Surname: " + manager.surname));
			
			if(manager.restaurant != null){
				let restaurantName = document.getElementById("restaurant");
				let link = document.createElement("a");
				link.appendChild(document.createTextNode("Restaurant: " + manager.restaurant.name));
				link.href = "restaurant_administrator.html?name=" + manager.restaurant.name + "&location=" + 
				manager.restaurant.location.cityName + ", " + manager.restaurant.location.streetName + " " + manager.restaurant.location.streetNumber;
				
				restaurantName.appendChild(link);
			}
		}
	})
	$("#delete").click(function(e){
		$.ajax({
			url: "rest/AdministratorService/deleteManager",
			type: 'DELETE',
			data: JSON.stringify(manager),
			contentType: 'application/json',
			complete: function(message){
				alert("Manager has been deleted");
				window.location.replace("users_administrator.html");
			}
		})
	})
})