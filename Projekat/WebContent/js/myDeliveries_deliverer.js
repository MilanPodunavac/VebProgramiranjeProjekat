function getDeliveries(){
	$.get({
		url: "rest/DelivererService/getDelivererDeliveries",
		contetType: 'application/json',
		complete: function(message){
			deliveries = JSON.parse(message.responseText);
			let tabela = document.getElementById("delivery_table");
			
			for(let delivery of deliveries){
				
				let deliveryTr = document.createElement('tr');
				let idTd = document.createElement('td');
				let restaurantTd = document.createElement('td');
				let typeTd = document.createElement('td');
				let articlesTd = document.createElement('td');
				let dateTd = document.createElement('td');
				let priceTd = document.createElement('td');
				let customerTd = document.createElement('td');
				let statusTd = document.createElement('td');
				let requestTd = document.createElement('td');
				
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
				customerTd.appendChild(document.createTextNode(delivery.customer.name + " " + delivery.customer.surname));
				statusTd.appendChild(document.createTextNode(delivery.deliveryStatus));
				
				if(delivery.deliveryStatus === "inDelivery"){
					let button = document.createElement('button');
					button.appendChild(document.createTextNode("Deliver"));
					$(button).click(function(){
						$.post({
				            url: "rest/DelivererService/deliverDelivery",
				            data: JSON.stringify(delivery),
				            contentType: 'application/json',
				            complete: function(message) {
				            	let rows = delivery_table.rows;
				            	for(var i = 1 ; i <  rows.length ; i++){
				            		if(rows[i].getElementsByTagName('td')[0].innerText == delivery.id){
				            			delivery_table.rows[i].deleteCell(8);
				            			(delivery_table.rows[i].getElementsByTagName('td'))[7].innerText = "delivered";
				            			break;
				            		}
				            	}
				            }
				     	});
					})
					requestTd.appendChild(button);
				}
				
				requestTd.style.textAlign = "center";
				
				
				deliveryTr.appendChild(idTd);
				deliveryTr.appendChild(restaurantTd);
				deliveryTr.appendChild(typeTd);
				deliveryTr.appendChild(articlesTd);
				deliveryTr.appendChild(dateTd);
				deliveryTr.appendChild(priceTd);
				deliveryTr.appendChild(customerTd);
				deliveryTr.appendChild(statusTd);
				deliveryTr.appendChild(requestTd);
				
				tabela.appendChild(deliveryTr);
			}
			
		}
	})
}
$(document).ready(function(){
	getDeliveries();
})