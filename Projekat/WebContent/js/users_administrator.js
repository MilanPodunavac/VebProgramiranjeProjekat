$(document).ready(function(){
	$.get({
		url: "rest/AdministratorService/getAllCustomers",
		contetType: 'application/json',
		complete: function(message){
			customers = JSON.parse(message.responseText);
			customer_list = document.getElementById("customer_list");
			for(customer of customers){
				let customerTr = document.createElement('tr');
				let usernameTd = document.createElement('td');
				let nameTd = document.createElement('td');
				let surnameTd = document.createElement('td');
				
				usernameTd.appendChild(document.createTextNode(customer.username));
				nameTd.appendChild(document.createTextNode(customer.name));
				surnameTd.appendChild(document.createTextNode(customer.surname));
				
				customerTr.appendChild(usernameTd);
				customerTr.appendChild(nameTd);
				customerTr.appendChild(surnameTd);
				
				customer_list.appendChild(customerTr);
			}
		}
	})
	$.get({
		url: "rest/AdministratorService/getAllDeliverers",
		contetType: 'application/json',
		complete: function(message){
			deliverers = JSON.parse(message.responseText);
			deliverer_list = document.getElementById("deliverer_list");
			for(deliverer of deliverers){
				let delivererTr = document.createElement('tr');
				let usernameTd = document.createElement('td');
				let nameTd = document.createElement('td');
				let surnameTd = document.createElement('td');
				
				usernameTd.appendChild(document.createTextNode(deliverer.username));
				nameTd.appendChild(document.createTextNode(deliverer.name));
				surnameTd.appendChild(document.createTextNode(deliverer.surname));
				
				delivererTr.appendChild(usernameTd);
				delivererTr.appendChild(nameTd);
				delivererTr.appendChild(surnameTd);
				
				deliverer_list.appendChild(delivererTr);
			}
		}
	})
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
				let restaurantNameTd = document.createElement('td');
				
				usernameTd.appendChild(document.createTextNode(manager.username));
				nameTd.appendChild(document.createTextNode(manager.name));
				surnameTd.appendChild(document.createTextNode(manager.surname));
				if(manager.restaurant != null){
					restaurantNameTd.appendChild(document.createTextNode(manager.restaurant.name));
				}
				else{
					restaurantNameTd.appendChild(document.createTextNode("No restaurant"));
				}
				
				managerTr.appendChild(usernameTd);
				managerTr.appendChild(nameTd);
				managerTr.appendChild(surnameTd);
				managerTr.appendChild(restaurantNameTd);
				
				manager_list.appendChild(managerTr);
			}
		}
	})
	$("#customer_list").on("click", "tr", function(){
		let customer_username = $(this).children("td:first").text();
		if(customer_username == "")return;
		window.location = "customerView_administrator.html?username=" + customer_username;
	})
	$("#manager_list").on("click", "tr", function(){
		let manager_username = $(this).children("td:first").text();
		if(manager_username == "")return;
		window.location = "managerView_administrator.html?username=" + manager_username;
	})
	$("#deliverer_list").on("click", "tr", function(){
		let deliverer_username = $(this).children("td:first").text();
		if(deliverer_username == "")return;
		window.location = "delivererView_administrator.html?username=" + deliverer_username;
	})
	$("#createDeliverer").click(function(){
		window.location = "createDeliverer_administrator.html";
	})
	$("#createManager").click(function(){
		window.location = "createManager_administrator.html";
	})
})