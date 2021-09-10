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
					//logoTd.appendChild(document.createTextNode(restaurant.logo));
					if(restaurant.imageId != ""){
						$.get({
							url: "rest/Image64Service/getImageData?id=" + restaurant.imageId,
							complete: function(message){
								let imageData64 = message.responseText;
								console.log(imageData64);
								var image = document.createElement('img');
								image.src = imageData64;
								image.style.width = 'auto';
								image.style.height = '70px';
								
								logoTd.appendChild(image);
								
							}
						})
					}
					else{
						logoTd.appendChild(document.createTextNode("No logo"));
					}
					logoTd.style.textAlign = "center";
					
					$.post({
						url: "rest/RestaurantService/getAverageGrade",
						data: JSON.stringify(restaurant),
						contentType: 'application/json',
						complete: function(m){
							let grade = JSON.parse(m.responseText);
							if(grade == 0){
								gradeTd.appendChild(document.createTextNode("No grades"));
							}
							else {
								gradeTd.appendChild(document.createTextNode(grade));
							}
						}
					})

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



