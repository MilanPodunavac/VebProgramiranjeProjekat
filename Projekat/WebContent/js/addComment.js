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

	let restaurant_name_paragraph = document.getElementById("restaurant_name");
	restaurant_name_paragraph.innerText = restaurant_name;
	
	$("#addCommentButton").click(function(){
		let commentGrade = document.getElementById("grade").value;
		let commentText = document.getElementById("commentText").value;
		$.post({
			url: "rest/CustomerService/postComment?name=" + restaurant_name + "&cityName=" + restaurant_cityname + "&streetName=" + restaurant_streetname + "&streetNumber=" + restaurant_streetnumber,
			data: JSON.stringify({ text: commentText, grade: commentGrade, approved: false, customer: null, restaurant: null}),
			contentType: 'application/json',
			complete: function(message){
				window.location = "restaurant_customer.html?" + parameters;
			}
		})
	})
})