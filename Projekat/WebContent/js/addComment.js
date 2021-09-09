$(document).ready(function(){
	var url = window.location.href;
	var parameters = url.split("?")[1];
	parameters = decodeURIComponent(parameters);
	
	var name_parameter = parameters.split("&")[0];
	var restaurant_name = name_parameter.split("=")[1];
	
	var cityname_parameter = parameters.split("&")[1];
	var restaurant_cityname = cityname_parameter.split("=")[1];
	
	var streetname_parameter = parameters.split("&")[2];
	var restaurant_streetname = streetname_parameter.split("=")[1];
	
	var streetnumber_parameter = parameters.split("&")[3];
	var restaurant_streetnumber = streetnumber_parameter.split("=")[1];

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
				window.location = "deliveries_customer.html";
			}
		})
	})
})