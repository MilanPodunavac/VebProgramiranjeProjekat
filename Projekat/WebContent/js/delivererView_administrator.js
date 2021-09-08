var deliverer;
$(document).ready(function(){
	var url = window.location.href;
	var parameters = url.split("?")[1];
	var username_parameter = parameters.split("&")[0];
	var username = username_parameter.split("=")[1];
	username = decodeURIComponent(username);
	$.get({
	    url: "rest/DelivererService/getDelivererByUsername?username=" + username,
	    contentType: 'application/json',
	    complete: function(message) {
			deliverer = JSON.parse(message.responseText);
			let titleTag = document.getElementsByTagName("title")[0];
			titleTag.innerText = deliverer.username;
			
			let delivererUsernameTd = document.getElementById("username_td");
			delivererUsernameTd.appendChild(document.createTextNode("Username: " + deliverer.username));
			
			let delivererNameTd = document.getElementById("name_td");
			delivererNameTd.appendChild(document.createTextNode("Name: " + deliverer.name));
			
			let delivererSurnameTd = document.getElementById("surname_td");
			delivererSurnameTd.appendChild(document.createTextNode("Surname: " + deliverer.surname));
			
			delivery_list = document.getElementById("delivery_list");
			
			for(let delivery of deliverer.deliveries){
				let deliveryTr = document.createElement('tr');
				let restaurantNameTd = document.createElement('td');
				let priceTd = document.createElement('td');
				let statusTd = document.createElement('td');
				
				restaurantNameTd.appendChild(document.createTextNode(delivery.restaurant.name));
				priceTd.appendChild(document.createTextNode(delivery.totalCost));
				statusTd.appendChild(document.createTextNode(delivery.deliveryStatus));
				
				deliveryTr.appendChild(restaurantNameTd);
				deliveryTr.appendChild(priceTd);
				deliveryTr.appendChild(statusTd);
				
				delivery_list.appendChild(deliveryTr);
			}
		}
	})
	$("#delete").click(function(e){
		$.ajax({
			url: "rest/AdministratorService/deleteDeliverer",
			type: 'DELETE',
			data: JSON.stringify(deliverer),
			contentType: 'application/json',
			complete: function(message){
				alert("Restaurant has been deleted");
				window.location.replace("users_administrator.html");
			}
		})
	})
})