var restaurants;

$(document).ready(function(){
	
	$.get({
            url: "rest/RestaurantService/RestaurantsSortedByWorking",
            contentType: 'application/json',
            complete: function(message) {
                restaurants = JSON.parse(message.responseText);

				let tabela = document.getElementById("restaurant_list");

				for (let restaurant of restaurants) {
					
					let restaurantTr = document.createElement('tr');
					let nameTd = document.createElement('td');
					let typeTd = document.createElement('td');
					let locationTd = document.createElement('td');
					let logoTd = document.createElement('td');
					let gradeTd = document.createElement('td');
					let openTd = document.createElement('td');
					
					nameTd.appendChild(document.createTextNode(restaurant.name));
					typeTd.appendChild(document.createTextNode(restaurant.restaurantType));
					locationTd.appendChild(document.createTextNode(restaurant.location.cityName + ", " + restaurant.location.streetName + " " + restaurant.location.streetNumber));
					logoTd.appendChild(document.createTextNode(restaurant.logo));
					gradeTd.appendChild(document.createTextNode("1"));
					if(restaurant.working){
						openTd.appendChild(document.createTextNode("Open"));
					}
					else{
						openTd.appendChild(document.createTextNode("Closed"));
					}
					
					restaurantTr.appendChild(nameTd);
					restaurantTr.appendChild(typeTd);
					restaurantTr.appendChild(locationTd);
					restaurantTr.appendChild(logoTd);
					restaurantTr.appendChild(gradeTd);
					restaurantTr.appendChild(openTd);
					
					tabela.appendChild(restaurantTr);
				}
            }
     });
	
});



