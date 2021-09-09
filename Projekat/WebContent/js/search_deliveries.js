$(document).ready(function(){
	$("#search_button").click(function(e) {
		e.preventDefault();
		var search_restaurant = $("#search_restaurant").val().toUpperCase();
		var search_article = $("#search_article").val().toUpperCase();
		var status_filter = $('input[name=status_filter]:checked', '#status_filter').val().toUpperCase();
		
		var table = document.getElementById("deliveries_list");
		var tr = table.getElementsByTagName("tr");
		for(let i = 1; i < tr.length; i++){
			let restaurant_name_td = tr[i].getElementsByTagName('td')[1];
			let restaurant_name = restaurant_name_td.textContent || restaurant_name_td.innerText;
			
			let article_list_elements = tr[i].getElementsByTagName('td')[2].getElementsByTagName('ul')[0].getElementsByTagName('li');
			const article_list = [];
			for(let j = 0; j < article_list_elements.length; j++){
				let article_name = article_list_elements[j].innerText.split(',')[0];
				article_list.push(article_name.toUpperCase());
			}
			
			let status_td = tr[i].getElementsByTagName('td')[6];
			let delivery_status = status_td.textContent || status_td.innerText;
			
			tr[i].style.display = "";
			if(restaurant_name.toUpperCase().indexOf(search_restaurant) === -1){
				tr[i].style.display = "none";
			}
			else if(status_filter != "ALL" && status_filter != "ACTIVE" && delivery_status.toUpperCase().indexOf(status_filter) === -1){
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