$(document).ready(function(){
	$("#search_button").click(function(e) {
		e.preventDefault();
		var search_restaurant = $("#search_restaurant").val().toUpperCase();
		var search_article = $("#search_article").val().toUpperCase();
		var search_customer = "";
		var search_customer_val =  $("#search_customer").val();
		if(search_customer_val) {
			search_customer = search_customer_val.toUpperCase();
		}
		
		var search_price_bottom = parseInt($("#search_price_bottom").val());
		if(search_price_bottom === NaN){
			search_price_bottom = 0;
		}
		
		var search_price_top = parseInt($("#search_price_top").val());
		if(search_price_top === NaN){
			search_price_top = 999999999999;
		}
		
		var search_date_bottom =  new Date($("#search_date_bottom").val());
		var search_date_top =  new Date($("#search_date_top").val());
		
		var status_filter = "ALL";
		var status_filter_val = $('input[name=status_filter]:checked', '#status_filter').val();
		if(status_filter_val) {
			status_filter = status_filter_val.toUpperCase();
		}
		var type_filter = $('input[name=type_filter]:checked', '#type_filter').val().toUpperCase();
		
		var table = document.getElementById("delivery_table");
		var tr = table.getElementsByTagName("tr");
		for(let i = 1; i < tr.length; i++){
			let restaurant_name_td = tr[i].getElementsByTagName('td')[1];
			let restaurant_name = restaurant_name_td.textContent || restaurant_name_td.innerText;
			
			let restaurant_type_td = tr[i].getElementsByTagName('td')[2];
			let restaurant_type = restaurant_type_td.textContent || restaurant_type_td.innerText;
			
			let article_list_elements = tr[i].getElementsByTagName('td')[3].getElementsByTagName('ul')[0].getElementsByTagName('li');
			const article_list = [];
			for(let j = 0; j < article_list_elements.length; j++){
				let article_name = article_list_elements[j].innerText.split(',')[0];
				article_list.push(article_name.toUpperCase());
			}
			
			let date_td = tr[i].getElementsByTagName('td')[4];
			let date_string = date_td.textContent || date_td.innerText;
			let delivery_date = new Date(date_string);
			
			let price_td = tr[i].getElementsByTagName('td')[5];
			let delivery_price = parseFloat(price_td.textContent || price_td.innerText);
			
			let customer_td = tr[i].getElementsByTagName('td')[6];
			let delivery_customer = customer_td.textContent || customer_td.innerText;
			
			let status_td = tr[i].getElementsByTagName('td')[7];
			let delivery_status = status_td.textContent || status_td.innerText;
			
			tr[i].style.display = "";
			if(restaurant_name.toUpperCase().indexOf(search_restaurant) === -1){
				tr[i].style.display = "none";
			}
			else if(delivery_customer.toUpperCase().indexOf(search_customer) === -1){
				tr[i].style.display = "none";
			}
			else if(type_filter != "ALL" && restaurant_type.toUpperCase().indexOf(type_filter) === -1){
				tr[i].style.display = "none";
			}
			else if(status_filter != "ALL" && status_filter != "ACTIVE" && delivery_status.toUpperCase().indexOf(status_filter) === -1){
				tr[i].style.display = "none";
			}
			else if(delivery_price < search_price_bottom || delivery_price > search_price_top){
				tr[i].style.display = "none";
			}
			else if((search_date_bottom != "Invalid Date" && delivery_date < search_date_bottom) || (search_date_top != "Invalid Date" && delivery_date > search_date_top)){
				tr[i].style.display = "none";
			}
			else{
				if(status_filter == "ACTIVE" && (delivery_status == "delivered" || delivery_status == "cancelled")){
					tr[i].style.display = "none";
				}
				if(article_list.find(element => element.indexOf(search_article) != -1) === undefined){
					tr[i].style.display = "none";
				}
			}
			
		}
		
	})
})