$(document).ready(function(){
	$("#search_button").click(function(e) {
		e.preventDefault();
		var search_username = $("#search_username").val().toUpperCase();
		var search_name = $("#search_name").val().toUpperCase();
		var search_surname = $("#search_surname").val().toUpperCase();
		
		var type_filter = "ALL";
		var type_filter_val = $('input[name=type_filter]:checked', '#type_filter').val();
		if(type_filter_val) {
			type_filter = type_filter_val.toUpperCase();
		}
		
		var gender_filter = $('input[name=gender_filter]:checked', '#gender_filter').val().toUpperCase();
		
		
		var table = document.getElementById("customer_list");
		if(!table){
			table = document.getElementById("manager_list")
		}
		if(!table){
			table = document.getElementById("deliverer_list")
		}
		var tr = table.getElementsByTagName("tr");
		for(let i = 1; i < tr.length; i++){
			let username_td = tr[i].getElementsByTagName('td')[0];
			let username = username_td.textContent || username_td.innerText;
			
			let name_td = tr[i].getElementsByTagName('td')[1];
			let name = name_td.textContent || name_td.innerText;
			
			let surname_td = tr[i].getElementsByTagName('td')[2];
			let surname = surname_td.textContent || surname_td.innerText;
			
			let gender_td = tr[i].getElementsByTagName('td')[3];
			let gender = gender_td.textContent || gender_td.innerText;
			
			let type_td = tr[i].getElementsByTagName('td')[6];
			let type = "";
			if(type_td){
				type = type_td.textContent || type_td.innerText;
			}

			tr[i].style.display = "";
			if(username.toUpperCase().indexOf(search_username) === -1){
				tr[i].style.display = "none";
			}
			else if(name.toUpperCase().indexOf(search_name) === -1){
				tr[i].style.display = "none";
			}
			else if(surname.toUpperCase().indexOf(search_surname) === -1){
				tr[i].style.display = "none";
			}
			else if(gender_filter != "ALL" && gender.toUpperCase() != gender_filter){
				tr[i].style.display = "none";
			}
			else if(type_filter != "ALL" && type.toUpperCase().indexOf(type_filter) === -1){
				tr[i].style.display = "none";
			}
			
		}
		
	})
})