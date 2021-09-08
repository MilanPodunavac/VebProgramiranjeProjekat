var customer;
$(document).ready(function(){
	var url = window.location.href;
	var parameters = url.split("?")[1];
	var username_parameter = parameters.split("&")[0];
	var username = username_parameter.split("=")[1];
	username = decodeURIComponent(username);
	$.get({
	    url: "rest/CustomerService/getCustomerByUsername?username=" + username,
	    contentType: 'application/json',
	    complete: function(message) {
			customer = JSON.parse(message.responseText);
			let titleTag = document.getElementsByTagName("title")[0];
			titleTag.innerText = customer.username;
			
			let customerUsernameTd = document.getElementById("username_td");
			customerUsernameTd.appendChild(document.createTextNode("Username: " + customer.username));
			
			let customerNameTd = document.getElementById("name_td");
			customerNameTd.appendChild(document.createTextNode("Name: " + customer.name));
			
			let customerSurnameTd = document.getElementById("surname_td");
			customerSurnameTd.appendChild(document.createTextNode("Surname: " + customer.surname));
			
			let customerTypeTd = document.getElementById("customerType_td");
			customerTypeTd.appendChild(document.createTextNode("Type: " + customer.customerType.name));
			
			delivery_list = document.getElementById("delivery_list");
			
			for(let delivery of customer.deliveries){
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
			url: "rest/AdministratorService/deleteCustomer",
			type: 'DELETE',
			data: JSON.stringify(customer),
			contentType: 'application/json',
			complete: function(message){
				alert("Restaurant has been deleted");
				window.location.replace("users_administrator.html");
			}
		})
	})
})