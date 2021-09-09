$(document).ready(function(){
	$.get({
		url: "rest/ManagerService/getManager",
		contentType: "application/json",
		complete: function(message){
			manager = JSON.parse(message.responseText);
			if(manager.restaurant == null){
				alert("Sorry, you have no restaurant");
				window.location = "manager.html";
			}
			restaurant = manager.restaurant;
			
			let restaurant_cityName = restaurant.location.cityName;
			let restaurant_streetName = restaurant.location.streetName;
			let restaurant_streetnumber = restaurant.location.streetNumber;
			
			$.get({
	            url: "rest/RestaurantService/getRestaurantByNameAndLocation?name=" + restaurant.name + "&cityName=" + restaurant_cityName + "&streetName=" + restaurant_streetName + "&streetNumber=" + restaurant_streetnumber,
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
						
						articleEditTd = document.createElement("td");
						articleEditButton = document.createElement("button");
						articleEditButton.appendChild(document.createTextNode("Edit"));
						$(articleEditButton).click(function(){
							
						})
						
						articleEditTd.appendChild(articleEditButton);
						
						articleTr.appendChild(nameTd);
						articleTr.appendChild(typeTd);
						articleTr.appendChild(sizeTd);
						articleTr.appendChild(priceTd);
						articleTr.appendChild(pictureTd);
						articleTr.appendChild(descriptionTd);
						articleTr.appendChild(articleEditTd);
						
						articleTable.appendChild(articleTr);
					}
					
				}
			})
			$.get({
				url: "rest/RestaurantService/getApprovedRestaurantComments?name=" + restaurant.name + "&cityName=" + restaurant_cityName + "&streetName=" + restaurant_streetName + "&streetNumber=" + restaurant_streetnumber,
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
		}
	})
})