function getDeliveries(){
	$.get({
		url: "rest/DelivererService/getDelivererDeliveries",
		contetType: 'application/json',
		complete: function(message){
			deliveries = JSON.parse(message.responseText);
			let delivery_table = document.getElementById("delivery_table");
			
			for(let delivery of deliveries){
				let deliveryTr = document.createElement("tr");
				let customerTd = document.createElement("td");
				let totalCostTd = document.createElement("td");
				let restaurantTd = document.createElement("td");
				let requestTd = document.createElement('td');
				let button = document.createElement('button');
				
				let idTd = document.createElement('td');
				idTd.hidden = true;
				idTd.appendChild(document.createTextNode(delivery.id));
				
				customerTd.appendChild(document.createTextNode(delivery.customer.name + " " + delivery.customer.surname));
				totalCostTd.appendChild(document.createTextNode(delivery.totalCost));
				restaurantTd.appendChild(document.createTextNode(delivery.restaurant.name));
				
				button.appendChild(document.createTextNode("request delivery"));
				$(button).click(function(){
					$.post({
			            url: "rest/DelivererService/deliverDelivery",
			            data: JSON.stringify(delivery),
			            contentType: 'application/json',
			            complete: function(message) {
			            	let rows = delivery_table.rows;
			            	for(var i = 1 ; i <  rows.length ; i++){
			            		if(rows[i].getElementsByTagName('td')[0].innerText == delivery.id){
			            			delivery_table.deleteRow(i);
			            			break;
			            		}
			            	}
			            }
			     	});
				})
				requestTd.appendChild(button);
				
				deliveryTr.appendChild(idTd);
				deliveryTr.appendChild(customerTd);
				deliveryTr.appendChild(totalCostTd);
				deliveryTr.appendChild(restaurantTd);
				deliveryTr.appendChild(requestTd);
				
				delivery_table.appendChild(deliveryTr);
			}
			
		}
	})
}
$(document).ready(function(){
	getDeliveries();
})