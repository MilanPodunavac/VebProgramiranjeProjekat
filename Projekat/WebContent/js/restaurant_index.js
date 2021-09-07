$(document).ready(function(){
	$("#restaurant_list").on("click", "tr", function(){
		let restaurant_name = $(this).children("td:first").text();
		let restaurant_location = $(this).children("td:nth-child(3)").text();
	
		window.location = "restaurant_index.html?name=" + restaurant_name + "&location=" + restaurant_location;
	});
})