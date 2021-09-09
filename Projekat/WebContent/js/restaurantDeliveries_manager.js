$(document).ready(function(){
	$.get({
		url: "rest/ManagerService/getManager",
		contentType: 'application/json',
		complete: function(message){
			manager = JSON.parse(message.responseText);
			if(manager.restaurant == null){
				alert("Sorry, you have no restaurant");
				window.location = manager.html;
			}
			$.get({
				url: "rest/ManagerService/getRestaurantDeliveries",
				contentType: 'application/json',
				complete: function(message){
					deliveries = JSON.parse(message.responseText);
					let processing_table = document.getElementById("processing_table");
					let preparation_table = document.getElementById("preparation_table");
					let waitingDelivery_table = document.getElementById("waitingDelivery_table");
					let inDelivery_table = document.getElementById("inDelivery_table");
					let delivered_table = document.getElementById("delivered_table");
					let cancelled_table = document.getElementById("cancelled_table");
					for(let delivery of deliveries){
						let deliveryTr = document.createElement("tr");
						
						let deliveryIdTd = document.createElement("td");
						let customerNameTd = document.createElement("td");
						let totalCostTd = document.createElement("td");
						
						deliveryIdTd.hidden = true;
						deliveryIdTd.appendChild(document.createTextNode(delivery.id));
						
						customerNameTd.appendChild(document.createTextNode(delivery.customer.name + " " + delivery.customer.surname));
						totalCostTd.appendChild(document.createTextNode(delivery.totalCost));
						
						deliveryTr.appendChild(deliveryIdTd);
						deliveryTr.appendChild(customerNameTd);
						deliveryTr.appendChild(totalCostTd);
						
						if(delivery.deliveryStatus == "processing"){
							let prepareTd = document.createElement("td");
							let prepareBtn = document.createElement("button");
							
							prepareBtn.appendChild(document.createTextNode("Prepare"));
							$(prepareBtn).click(function(){
								$.post({
									url: "rest/ManagerService/setDeliveryToPreparation",
									contentType: 'application/json',
									data: JSON.stringify(delivery),
									complete: function(message){
										rows = processing_table.rows;
										for(var i = 1 ; i <  rows.length ; i++){
						            		if(rows[i].getElementsByTagName('td')[0].innerText == delivery.id){
						            			processing_table.deleteRow(i);
												break;
						            		}
						            	}
									let preparationDeliveryTr = document.createElement("tr");
						
									let preparationDeliveryIdTd = document.createElement("td");
									let preparationCustomerNameTd = document.createElement("td");
									let preparationTotalCostTd = document.createElement("td");
									
									preparationDeliveryIdTd.hidden = true;
									preparationDeliveryIdTd.appendChild(document.createTextNode(delivery.id));
									
									preparationCustomerNameTd.appendChild(document.createTextNode(delivery.customer.name + " " + delivery.customer.surname));
									preparationTotalCostTd.appendChild(document.createTextNode(delivery.totalCost));
									
									preparationDeliveryTr.appendChild(preparationDeliveryIdTd);
									preparationDeliveryTr.appendChild(preparationCustomerNameTd);
									preparationDeliveryTr.appendChild(preparationTotalCostTd);
									
									let inDeliveryTd = document.createElement("td");
									let inDeliveryBtn = document.createElement("button");
									
									inDeliveryBtn.appendChild(document.createTextNode("Wait delivery"));
									$(inDeliveryBtn).click(function(){
										rows = preparation_table.rows;
										for(var i = 1 ; i <  rows.length ; i++){
						            		if(rows[i].getElementsByTagName('td')[0].innerText == delivery.id){
						            			preparation_table.deleteRow(i);
												break;
						            		}
						            	}
										let waitingDeliveryDeliveryTr = document.createElement("tr");
						
										let waitingDeliveryDeliveryIdTd = document.createElement("td");
										let waitingDeliveryCustomerNameTd = document.createElement("td");
										let waitingDeliveryTotalCostTd = document.createElement("td");
										
										waitingDeliveryDeliveryIdTd.hidden = true;
										waitingDeliveryDeliveryIdTd.appendChild(document.createTextNode(delivery.id));
										
										waitingDeliveryCustomerNameTd.appendChild(document.createTextNode(delivery.customer.name + " " + delivery.customer.surname));
										waitingDeliveryTotalCostTd.appendChild(document.createTextNode(delivery.totalCost));
										
										waitingDeliveryDeliveryTr.appendChild(waitingDeliveryDeliveryIdTd);
										waitingDeliveryDeliveryTr.appendChild(waitingDeliveryCustomerNameTd);
										waitingDeliveryDeliveryTr.appendChild(waitingDeliveryTotalCostTd);
										
										waitingDelivery_table.appendChild(waitingDeliveryDeliveryTr);
									});
									inDeliveryTd.appendChild(inDeliveryBtn);
									
									preparationDeliveryTr.appendChild(inDeliveryTd);
									
									preparation_table.appendChild(preparationDeliveryTr);
									}
								})
							});
							prepareTd.appendChild(prepareBtn);
							
							deliveryTr.appendChild(prepareTd);
							
							processing_table.appendChild(deliveryTr);
						}
						if(delivery.deliveryStatus == "preparation"){
							let inDeliveryTd = document.createElement("td");
							let inDeliveryBtn = document.createElement("button");
							
							inDeliveryBtn.appendChild(document.createTextNode("Wait delivery"));
							$(inDeliveryBtn).click(function(){
								$.post({
									url: "rest/ManagerService/setDeliveryToWaitingDelivery",
									contentType: 'application/json',
									data: JSON.stringify(delivery),
									complete: function(message){
										rows = preparation_table.rows;
										for(var i = 1 ; i <  rows.length ; i++){
						            		if(rows[i].getElementsByTagName('td')[0].innerText == delivery.id){
						            			preparation_table.deleteRow(i);
												break;
						            		}
						            	}
										let waitingDeliveryDeliveryTr = document.createElement("tr");
						
										let waitingDeliveryDeliveryIdTd = document.createElement("td");
										let waitingDeliveryCustomerNameTd = document.createElement("td");
										let waitingDeliveryTotalCostTd = document.createElement("td");
										
										waitingDeliveryDeliveryIdTd.hidden = true;
										waitingDeliveryDeliveryIdTd.appendChild(document.createTextNode(delivery.id));
										
										waitingDeliveryCustomerNameTd.appendChild(document.createTextNode(delivery.customer.name + " " + delivery.customer.surname));
										waitingDeliveryTotalCostTd.appendChild(document.createTextNode(delivery.totalCost));
										
										waitingDeliveryDeliveryTr.appendChild(waitingDeliveryDeliveryIdTd);
										waitingDeliveryDeliveryTr.appendChild(waitingDeliveryCustomerNameTd);
										waitingDeliveryDeliveryTr.appendChild(waitingDeliveryTotalCostTd);
										
										waitingDelivery_table.appendChild(waitingDeliveryDeliveryTr);
									}
								})
							});
							inDeliveryTd.appendChild(inDeliveryBtn);
							
							deliveryTr.appendChild(inDeliveryTd);
							
							preparation_table.appendChild(deliveryTr);
						}
						if(delivery.deliveryStatus == "waitingDelivery"){							
							waitingDelivery_table.appendChild(deliveryTr);
						}
						if(delivery.deliveryStatus == "inDelivery"){
							inDelivery_table.appendChild(deliveryTr);
						}
						if(delivery.deliveryStatus == "delivered"){
							delivered_table.appendChild(deliveryTr);
						}
						if(delivery.deliveryStatus == "cancelled"){
							cancelled_table.appendChild(deliveryTr);
						}
					}
				}
				
			})
		}
	})
})