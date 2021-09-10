$(document).ready(function(){
	$.get({
		url: "rest/AdministratorService/getAllManagers",
		contetType: 'application/json',
		complete: function(message){
			managers = JSON.parse(message.responseText);
			manager_list = document.getElementById("manager_list");
			for(manager of managers){
				let managerTr = document.createElement('tr');
				let usernameTd = document.createElement('td');
				let nameTd = document.createElement('td');
				let surnameTd = document.createElement('td');
				let genderTd = document.createElement('td');
				let dateOfBirthTd = document.createElement('td');
				let restaurantNameTd = document.createElement('td');
				
				usernameTd.appendChild(document.createTextNode(manager.username));
				nameTd.appendChild(document.createTextNode(manager.name));
				surnameTd.appendChild(document.createTextNode(manager.surname));
				genderTd.appendChild(document.createTextNode(manager.gender));
				let date = new Date(parseInt(manager.dateOfBirth));
				dateOfBirthTd.appendChild(document.createTextNode(date.toLocaleString()));
				if(manager.restaurant != null){
					restaurantNameTd.appendChild(document.createTextNode(manager.restaurant.name));
				}
				else{
					restaurantNameTd.appendChild(document.createTextNode("No restaurant"));
				}
				
				managerTr.appendChild(usernameTd);
				managerTr.appendChild(nameTd);
				managerTr.appendChild(surnameTd);
				managerTr.appendChild(genderTd);
				managerTr.appendChild(dateOfBirthTd);
				managerTr.appendChild(restaurantNameTd);
				
				manager_list.appendChild(managerTr);
			}
		}
	})
	
	$("#manager_list").on("click", "tr", function(){
		let manager_username = $(this).children("td:first").text();
		if(manager_username == "")return;
		window.location = "managerView_administrator.html?username=" + manager_username;
	})
	
	$("#createManager").click(function(){
		window.location = "createManager_administrator.html";
	})
	
})