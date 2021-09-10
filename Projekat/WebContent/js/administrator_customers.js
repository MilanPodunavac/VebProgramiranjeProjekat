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
				let genderTd = document.createElement('td');
				let dateOfBirthTd = document.createElement('td');
				let pointsTd = document.createElement('td');
				let customerTypeTd = document.createElement('td');
				
				usernameTd.appendChild(document.createTextNode(customer.username));
				nameTd.appendChild(document.createTextNode(customer.name));
				surnameTd.appendChild(document.createTextNode(customer.surname));
				genderTd.appendChild(document.createTextNode(customer.gender));
				let date = new Date(parseInt(customer.dateOfBirth));
				dateOfBirthTd.appendChild(document.createTextNode(date.toLocaleString()));
				pointsTd.appendChild(document.createTextNode(customer.points));
				customerTypeTd.appendChild(document.createTextNode(customer.customerType.name));
				
				customerTr.appendChild(usernameTd);
				customerTr.appendChild(nameTd);
				customerTr.appendChild(surnameTd);
				customerTr.appendChild(genderTd);
				customerTr.appendChild(dateOfBirthTd);
				customerTr.appendChild(pointsTd);
				customerTr.appendChild(customerTypeTd);
				
				customer_list.appendChild(customerTr);
			}
		}
	})
	
	
	$("#customer_list").on("click", "tr", function(){
		let customer_username = $(this).children("td:first").text();
		if(customer_username == "")return;
		window.location = "customerView_administrator.html?username=" + customer_username;
	})
	
})