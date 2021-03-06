$(document).ready(function(){
	
	$.get({
            url: "rest/CustomerService/getCustomer",
            contentType: 'application/json',
            complete: function(message) {
                var customer = JSON.parse(message.responseText);

				let tabela = document.getElementById("delivery_table");

				for (let delivery of customer.deliveries) {
					
					let deliveryTr = document.createElement('tr');
					let idTd = document.createElement('td');
					let restaurantTd = document.createElement('td');
					let typeTd = document.createElement('td');
					let articlesTd = document.createElement('td');
					let dateTd = document.createElement('td');
					let priceTd = document.createElement('td');
					let customerTd = document.createElement('td');
					let statusTd = document.createElement('td');
					let cancelTd = document.createElement('td');
					
					idTd.appendChild(document.createTextNode(delivery.id));
					restaurantTd.appendChild(document.createTextNode(delivery.restaurant.name));
					typeTd.appendChild(document.createTextNode(delivery.restaurant.restaurantType));
					
					var itemList = document.createElement('ul');
					for(let item of delivery.items){
						let itemLi = document.createElement('li');
						
						itemLi.appendChild(document.createTextNode(item.article.name + ", " + item.article.price + ", " + item.amount));
						
						itemList.appendChild(itemLi);
					}
					articlesTd.appendChild(itemList);
					
					let date = new Date(parseInt(delivery.time))
					dateTd.appendChild(document.createTextNode(date.toLocaleString()));
					priceTd.appendChild(document.createTextNode(delivery.totalCost));
					customerTd.appendChild(document.createTextNode(customer.name + " " + customer.surname));
					statusTd.appendChild(document.createTextNode(delivery.deliveryStatus));
					if(delivery.deliveryStatus == "processing"){
						let cancelButton = document.createElement('button');
						cancelButton.innerText = "Cancel";
						cancelTd.appendChild(cancelButton);
						$(cancelButton).click(function(){
							$.post({
								url: "rest/CustomerService/cancelDelivery",
								data: JSON.stringify(delivery),
								contentType: 'application/json',
								complete: function(message){
									alert("Delivery " + delivery.id + " cancelled");
									location.reload();
								}
							})
						})
					}
					else if(delivery.deliveryStatus == "delivered"){
						let commentButton = document.createElement('button');
						commentButton.innerText = "Comment";
						cancelTd.appendChild(commentButton);
						$(commentButton).click(function(){
							window.location = "addComment.html?name=" + delivery.restaurant.name + "&cityName=" + delivery.restaurant.location.cityName + "&streetName=" + delivery.restaurant.location.streetName + "&streetNumber=" + delivery.restaurant.location.streetNumber;
						})
					}
					cancelTd.style.textAlign = "center";
					
					deliveryTr.appendChild(idTd);
					deliveryTr.appendChild(restaurantTd);
					deliveryTr.appendChild(typeTd);
					deliveryTr.appendChild(articlesTd);
					deliveryTr.appendChild(dateTd);
					deliveryTr.appendChild(priceTd);
					deliveryTr.appendChild(customerTd);
					deliveryTr.appendChild(statusTd);
					deliveryTr.appendChild(cancelTd);
					
					tabela.appendChild(deliveryTr);
				}
				
            }
     });
	
});