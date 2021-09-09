$(document).ready(function(){
	var url = window.location.href;
	var parameters = url.split("?")[1];
	var name_parameter = parameters.split("&")[0];
	var location_parameter = parameters.split("&")[1];
	var restaurant_name = name_parameter.split("=")[1];
	var restaurant_location = location_parameter.split("=")[1];
	restaurant_name = decodeURIComponent(restaurant_name);
	restaurant_location = decodeURIComponent(restaurant_location);
	let restaurant_cityname = restaurant_location.split(", ")[0];
	let restaurant_citystreet = restaurant_location.split(", ")[1];
	let restaurant_streetparams = restaurant_citystreet.split(" ");
	let restaurant_streetnumber = restaurant_streetparams[restaurant_streetparams.length - 1];
	let restaurant_streetname = restaurant_citystreet.substring(0, restaurant_citystreet.length - restaurant_streetnumber.length - 1);
	
	let urlParts = window.location.href.split("/");
	let lastUrlPart = urlParts[urlParts.length - 1];
	
	$.get({
            url: "rest/RestaurantService/getRestaurantByNameAndLocation?name=" + restaurant_name + "&cityName=" + restaurant_cityname + "&streetName=" + restaurant_streetname + "&streetNumber=" + restaurant_streetnumber,
            contentType: 'application/json',
            complete: function(message) {
				let restaurant = JSON.parse(message.responseText);
				
				let titleTag = document.getElementsByTagName("title")[0];
				titleTag.innerText = restaurant.name;
				
				let restaurantNameTd = document.getElementById("name_td");
				restaurantNameTd.appendChild(document.createTextNode("Name: " + restaurant.name));
				
				let restaurantWorkingTd = document.getElementById("working_td");
				if(restaurant.working)
				{
					restaurantWorkingTd.appendChild(document.createTextNode("Open"));
				}
				else
				{
					restaurantWorkingTd.appendChild(document.createTextNode("Closed"));
				}
				
				let restaurantTypeTd = document.getElementById("type_td");
				restaurantTypeTd.appendChild(document.createTextNode("Type: " + restaurant.restaurantType));
				
				let restaurantLocationTd = document.getElementById("location_td");
				restaurantLocationTd.appendChild(document.createTextNode("Location: " + restaurant.location.cityName + " " + restaurant.location.cityNumber + ", " + restaurant.location.streetName + " " + restaurant.location.streetNumber));
	
				let restaurantLogoTd = document.getElementById("logo_td");
				restaurantLogoTd.appendChild(document.createTextNode("Your logo goes here"));
				
				var discount;
				var urlParts = window.location.href.split("/");
				var lastUrlPart = urlParts[urlParts.length - 1];
				if(lastUrlPart.startsWith("restaurant_customer")){	
					$.get({
						url: "rest/currentUser",
						contentType: 'application/json',
						complete: function(message) {
							let customer = JSON.parse(message.responseText);
							discount = customer.customerType.discount;
						}
					});
					
					$.post({
						url: "rest/CustomerService/clearShoppingCart",
						complete: function(message) {
							
						}
					})
					
					$("#orderButton").click(function(){
						let shoppingCartItemsTable = document.getElementById("shoppingCartItems_table");
						if(shoppingCartItemsTable.rows.length == 1){
							alert("No items in shopping cart, cannot order. Add items to the shopping cart to order.");
						}
						else{
							$.post({
								url: "rest/CustomerService/checkOut",
								data: JSON.stringify(restaurant),
								contentType: 'application/json',
								complete: function(message) {
									alert("Delivery ordered");
									window.location = "deliveries_customer.html";
								}
							})
						}
					})
					
					$.get({
						url: "rest/RestaurantService/getApprovedRestaurantComments?name=" + restaurant_name + "&cityName=" + restaurant_cityname + "&streetName=" + restaurant_streetname + "&streetNumber=" + restaurant_streetnumber,
						contentType: 'application/json',
						complete: function(message){
							let comments = JSON.parse(message.responseText);
							let commentTable = document.getElementById("comment_table");
							for(let comment of comments){
								let commentTr = document.createElement('tr');
								let userNameTd = document.createElement('td');
								let gradeTd = document.createElement('td');
								let textTd = document.createElement('td');
								
								userNameTd.appendChild(document.createTextNode(comment.customer.username));
								gradeTd.appendChild(document.createTextNode(comment.grade));
								textTd.appendChild(document.createTextNode(comment.text));
								
								commentTr.appendChild(userNameTd);
								commentTr.appendChild(gradeTd);
								commentTr.appendChild(textTd);
								
								commentTable.appendChild(commentTr);
							}
						}
					})
					$("#addCommentButton").click(function(){
						window.location = "addComment.html?" + parameters;
					})
				}

				var articleTable = document.getElementById("article_table");
				for(let article of restaurant.articles){
					
					let articleTr = document.createElement('tr');
					let nameTd = document.createElement('td');
					let typeTd = document.createElement('td');
					let sizeTd = document.createElement('td');
					let priceTd = document.createElement('td');
					let pictureTd = document.createElement('td');
					let descriptionTd = document.createElement('td');
					
					nameTd.appendChild(document.createTextNode(article.name));
					typeTd.appendChild(document.createTextNode(article.articleType));
					if(article.articleType === "food")
					{
						sizeTd.appendChild(document.createTextNode(article.size + "g"));
					}
					else
					{
						sizeTd.appendChild(document.createTextNode(article.size + "ml"));
					}
					priceTd.appendChild(document.createTextNode(article.price));
					pictureTd.appendChild(document.createTextNode(article.picture));
					descriptionTd.appendChild(document.createTextNode(article.description));
					
					
					articleTr.appendChild(nameTd);
					articleTr.appendChild(typeTd);
					articleTr.appendChild(sizeTd);
					articleTr.appendChild(priceTd);
					articleTr.appendChild(pictureTd);
					articleTr.appendChild(descriptionTd);
					
					
					if(lastUrlPart.startsWith("restaurant_customer")){
						
						let deliveryTd = document.createElement('td');
						deliveryTd.style.textAlign = "center";
						
						let minusButton = document.createElement('button');
						minusButton.innerText = "-";
						minusButton.style.marginRight = "7px";
						minusButton.disabled = true;
						minusButton.classList.add("minusButton");
						
						let numberText = document.createTextNode("0");
						
						let plusButton = document.createElement('button');
						plusButton.innerText = "+";
						plusButton.style.marginLeft = "7px";
						plusButton.classList.add("plusButton");
						
						$(minusButton).click(function(){
							let number = parseInt(numberText.textContent);
							number -= 1;
							$.post({
								url: "rest/CustomerService/removeItem",
					            data: JSON.stringify({ article: article, amount: number }),
					            contentType: 'application/json',
					            success: function(mes) {
					                if(number == 0){
										minusButton.disabled = true;
									}
									numberText.textContent = number;
									
									let totalCostTd = document.getElementById("price_td");
									totalCostTd.innerHTML = "";
									totalCostTd.appendChild(document.createTextNode("Total cost: " + mes.totalCost));
									
									let discountTd = document.getElementById("discount_td");
									discountTd.innerHTML = "";
									discountTd.appendChild(document.createTextNode("Discount: " + discount + "%"));
									
									let costTd = document.getElementById("cost_td");
									costTd.innerHTML = "";
									costTd.appendChild(document.createTextNode("Cost after discount: " + (mes.totalCost * (1 - discount / 100))));
									
									let pointsTd = document.getElementById("points_td");
									pointsTd.innerHTML = "";
									pointsTd.appendChild(document.createTextNode("Points gained: " + (mes.totalCost * 133 / 1000)));
									
									let shoppingCartItemsTable = document.getElementById("shoppingCartItems_table_tbody");
									shoppingCartItemsTable.innerHTML = "";
									for(let item of mes.items){
										
										let cartItemTr = document.createElement('tr');
										let nameTd = document.createElement('td');
										let priceTd = document.createElement('td');
										let amountTd = document.createElement('td');
										let itemCostTd = document.createElement('td');
										
										nameTd.appendChild(document.createTextNode(item.article.name));
										priceTd.appendChild(document.createTextNode(item.article.price));
										amountTd.appendChild(document.createTextNode(item.amount));
										itemCostTd.appendChild(document.createTextNode(item.amount * item.article.price));
										
										cartItemTr.appendChild(nameTd);
										cartItemTr.appendChild(priceTd);
										cartItemTr.appendChild(amountTd);
										cartItemTr.appendChild(itemCostTd);
										
										shoppingCartItemsTable.appendChild(cartItemTr);
									}
						        },
								fail: function(mes) {
									alert("failure");
								}
							})
						})
						$(plusButton).click(function(){
							let number = parseInt(numberText.textContent);
							number += 1;
							$.post({
								url: "rest/CustomerService/addItem",
					            data: JSON.stringify({ article: article, amount: number }),
					            contentType: 'application/json',
					            success: function(mes) {
					                if(number == 1){
										minusButton.disabled = false;
									}
									numberText.textContent = number;
									
									let totalCostTd = document.getElementById("price_td");
									totalCostTd.innerHTML = "";
									totalCostTd.appendChild(document.createTextNode("Total cost: " + mes.totalCost));
									
									let discountTd = document.getElementById("discount_td");
									discountTd.innerHTML = "";
									discountTd.appendChild(document.createTextNode("Discount: " + discount + "%"));
									
									let costTd = document.getElementById("cost_td");
									costTd.innerHTML = "";
									costTd.appendChild(document.createTextNode("Cost after discount: " + (mes.totalCost * (1 - discount / 100))));
									
									let pointsTd = document.getElementById("points_td");
									pointsTd.innerHTML = "";
									pointsTd.appendChild(document.createTextNode("Points gained: " + (mes.totalCost * 133 / 1000)));
									
									let shoppingCartItemsTable = document.getElementById("shoppingCartItems_table_tbody");
									shoppingCartItemsTable.innerHTML = "";
									for(let item of mes.items){
										
										let cartItemTr = document.createElement('tr');
										let nameTd = document.createElement('td');
										let priceTd = document.createElement('td');
										let amountTd = document.createElement('td');
										let itemCostTd = document.createElement('td');
										
										nameTd.appendChild(document.createTextNode(item.article.name));
										priceTd.appendChild(document.createTextNode(item.article.price));
										amountTd.appendChild(document.createTextNode(item.amount));
										itemCostTd.appendChild(document.createTextNode(item.amount * item.article.price));
										
										cartItemTr.appendChild(nameTd);
										cartItemTr.appendChild(priceTd);
										cartItemTr.appendChild(amountTd);
										cartItemTr.appendChild(itemCostTd);
										
										shoppingCartItemsTable.appendChild(cartItemTr);
									}
						        },
								fail: function(message) {
									alert("failure");
								}
							})
						})
						
						deliveryTd.appendChild(minusButton);
						deliveryTd.appendChild(numberText);
						deliveryTd.appendChild(plusButton);
						
						articleTr.appendChild(deliveryTd);
					}
					
					articleTable.appendChild(articleTr);
				}
				
				if(lastUrlPart.startsWith("restaurant_administrator")){
					$.get({
						url: "rest/RestaurantService/getAllrestaurantComments?name=" + restaurant_name + "&cityName=" + restaurant_cityname + "&streetName=" + restaurant_streetname + "&streetNumber=" + restaurant_streetnumber,
						contentType: 'application/json',
						complete: function(message){
							let comments = JSON.parse(message.responseText);
							let commentTable = document.getElementById("comment_table");
							for(let comment of comments){
								let commentTr = document.createElement('tr');
								let userNameTd = document.createElement('td');
								let gradeTd = document.createElement('td');
								let textTd = document.createElement('td');
								let approvedTd = document.createElement('td');
								
								userNameTd.appendChild(document.createTextNode(comment.customer.username));
								gradeTd.appendChild(document.createTextNode(comment.grade));
								textTd.appendChild(document.createTextNode(comment.text));
								if(comment.approved === true){
									approvedTd.appendChild(document.createTextNode("Yes"));
								}
								else{
									approvedTd.appendChild(document.createTextNode("No"));
								}
								commentTr.appendChild(userNameTd);
								commentTr.appendChild(gradeTd);
								commentTr.appendChild(textTd);
								commentTr.appendChild(approvedTd);
								
								commentTable.appendChild(commentTr);
							}
						}
					})
					$("#delete").click(function(e){
						alert(JSON.stringify(restaurant));
						$.ajax({
							url: "rest/AdministratorService/deleteRestaurant",
							type: 'DELETE',
							data: JSON.stringify(restaurant),
							contentType: 'application/json',
							complete: function(message){
								alert("Restaurant has been deleted");
								window.location.replace("administrator.html");
							}
						})
					})
				}
			}
			
	})
})