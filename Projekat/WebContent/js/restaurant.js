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
				if(restaurant.working){
						restaurantWorkingTd.appendChild(document.createTextNode("Open"));
					}
					else{
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
					if(article.articleType === "food"){
						sizeTd.appendChild(document.createTextNode(article.size + "g"));
					}
					else{
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
					
					let urlParts = window.location.href.split("/");
					let lastUrlPart = urlParts[urlParts.length - 1];
					if(lastUrlPart.startsWith("restaurant_customer")){
						let deliveryTd = document.createElement('td');
						deliveryTd.style.textAlign = "center";
						
						var minusButton = document.createElement('button');
						minusButton.innerText = "-";
						minusButton.style.marginRight = "7px";
						minusButton.disabled = true;
						minusButton.classList.add("minusButton");
						
						let numberText = document.createTextNode("0");
						
						var plusButton = document.createElement('button');
						plusButton.innerText = "+";
						plusButton.style.marginLeft = "7px";
						plusButton.classList.add("plusButton");
						
						
						
						minusButton.click(function(e){
							alert("minus");
							minusButton.disabled = true;
							plusButton.disabled = false;
						})
						plusButton.click(function(e){
							e.preventDefault();
							alert("plus");
							minusButton.disabled = false;
							plusButton.disabled = true;
						})
						
						deliveryTd.appendChild(minusButton);
						deliveryTd.appendChild(numberText);
						deliveryTd.appendChild(plusButton);
						
						articleTr.appendChild(deliveryTd);
					}
					
					articleTable.appendChild(articleTr);
				}
			}
	})
})