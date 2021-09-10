$(document).ready(function(){
	var url = window.location.href;
	var parameters = url.split("?")[1];
	var name_parameter = parameters.split("&")[0];
	var article_name = name_parameter.split("=")[1];
	article_name = decodeURIComponent(article_name);
	$.get({
		url: "rest/ManagerService/getArticle?name=" + article_name,
		contentType: 'application/json',
		complete: function(message){
			let article = JSON.parse(message.responseText);
			$("#name").val(article.name);
			$("#price").val(article.price);
			if(article.articleType == "food"){
				document.getElementById("type").selectedIndex = 0;
			}
			else{
				document.getElementById("type").selectedIndex = 1;
			}
			$("#size").val(article.size);
			$("#description").val(article.description);
			$("#confirm").click(function(){
				let name = $("#name").val();
				let price = $("#price").val();
				let type = $("#type").val();
				let size = $("#size").val();
				let description = $("#description").val();
				
				//	let image = $("#image").val();
				if(isNaN(price)){
					alert("Enter valid price!");
					return;
				}
				if(isNaN(size)){
					alert("Enter valid size!");
					return;
				}
				if(name.length == 0 || price.length == 0 || size.length == 0 || description == 0){
					alert("Fill in all fields!");
					return;
				}
				let priceFloat = parseFloat(price);
				let sizeInt = parseInt(size);
				if(priceFloat <= 0){
					alert("Price must be greater than 0!");
					return;
				}
				if(sizeInt <= 0){
					alert("Size must be greater than 0!");
					return;
				}
				let newArticle = {name: name, price: priceFloat, articleType: type, size: sizeInt, description: description}
				data = {article: newArticle, oldArticleName: article.name}
				$.post({
					url: "rest/ManagerService/updateArticle",
					contentType: "application/json",
					data: JSON.stringify(data),
					complete: function(message){
						alert("Article updated!")
						window.location = "myRestaurant_manager.html";
					}
				})
			})
		}
	})
})