$(document).ready(function(){
	$("#search_button").click(function(e) {
		e.preventDefault();
		var search_name = $("#search_name").val().toUpperCase();
		var search_location = $("#search_location").val().toUpperCase();
		var only_open_filter = $("#filter_open").is(":checked");
		var type_filter = $('input[name=type_filter]:checked', '#type_filter').val().toUpperCase();
		var grade_filter = $('input[name=grade_filter]:checked', '#grade_filter').val();
		
		var table = document.getElementById("restaurant_list");
		var tr = table.getElementsByTagName("tr");
		for(let i = 1; i < tr.length; i++){
			let restaurant_name_td = tr[i].getElementsByTagName('td')[0];
			let restaurant_name = restaurant_name_td.textContent || restaurant_name_td.innerText;
			
			let restaurant_type_td = tr[i].getElementsByTagName('td')[1];
			let restaurant_type = restaurant_type_td.textContent || restaurant_type_td.innerText;
			
			let restaurant_location_td = tr[i].getElementsByTagName('td')[2];
			let restaurant_location = restaurant_location_td.textContent || restaurant_location_td.innerText;
			
			let restaurant_grade_td = tr[i].getElementsByTagName('td')[4];
			let restaurant_grade = parseFloat(restaurant_grade_td.textContent || restaurant_grade_td.innerText);
			
			let restaurant_open_td = tr[i].getElementsByTagName('td')[5];
			let restaurant_open = restaurant_open_td.textContent || restaurant_open_td.innerText;
			
			tr[i].style.display = "";
			if(restaurant_name.toUpperCase().indexOf(search_name) === -1){
				tr[i].style.display = "none";
			}
			else if(type_filter != "ALL" && restaurant_type.toUpperCase().indexOf(type_filter) === -1){
				tr[i].style.display = "none";
			}
			else if(restaurant_location.toUpperCase().indexOf(search_location) === -1){
				tr[i].style.display = "none";
			}
			else if(only_open_filter && restaurant_open === "Closed"){
				tr[i].style.display = "none";
			}
			else if(grade_filter === "filter_grade2"){
				if(restaurant_grade <= 2){
					tr[i].style.display = "none";
				}
			}
			else if(grade_filter === "filter_grade3"){
				if(restaurant_grade <= 3){
					tr[i].style.display = "none";
				}
			}
			else if(grade_filter === "filter_grade4"){
				if(restaurant_grade <= 4){
					tr[i].style.display = "none";
				}
			}
		}
		
	})
})

