$(document).ready(function(){
	$.get({
		url: "rest/ManagerService/getPendingDeliveryRequestsForManager",
		contentType: 'application/json',
		complete: function(message){
			let deliveries = JSON.parse(message.responseText);
			let deliveryRequest_table = document.getElementById("deliveryRequest_table");
			
			for(let deliveryRequest of deliveries){
				let deliveryRequestTr = document.createElement('tr');
				let idTd = document.createElement('td');
				idTd.hidden=true;
				idTd.appendChild(document.createTextNode(deliveryRequest.delivery.id));
				let delivererNameTd = document.createElement('td');
				let customerNameTd = document.createElement('td');
				let totalCostTd = document.createElement('td');
				let buttonTd = document.createElement('td');
				let button = document.createElement('button');
				
				button.appendChild(document.createTextNode("Accept"));
				$(button).click(function(){
					var data = {delivererUsername: deliveryRequest.deliverer.username, deliveryId: deliveryRequest.delivery.id};
					$.post({
						url: "rest/ManagerService/setDeliveryToInDelivery",
						data: JSON.stringify(data),
						contentType: 'application/json',
						complete: function(message){
							let rows = deliveryRequest_table.rows;
			            	for(var i = 1 ; i <  rows.length ; i++){
			            		if(rows[i].getElementsByTagName('td')[0].innerText == deliveryRequest.delivery.id){
			            			deliveryRequest_table.deleteRow(i);
									i--;
			            		}
			            	}
						}
					})
				})
				buttonTd.appendChild(button);
				
				delivererNameTd.appendChild(document.createTextNode(deliveryRequest.deliverer.name + " " + deliveryRequest.deliverer.surname));
				totalCostTd.appendChild(document.createTextNode(deliveryRequest.delivery.totalCost));
				customerNameTd.appendChild(document.createTextNode(deliveryRequest.customer.name + " " + deliveryRequest.customer.surname));
				
				deliveryRequestTr.appendChild(idTd);
				deliveryRequestTr.appendChild(delivererNameTd);
				deliveryRequestTr.appendChild(customerNameTd);
				deliveryRequestTr.appendChild(totalCostTd);
				deliveryRequestTr.appendChild(buttonTd);
				
				deliveryRequest_table.appendChild(deliveryRequestTr);
			}
		}
	})
})