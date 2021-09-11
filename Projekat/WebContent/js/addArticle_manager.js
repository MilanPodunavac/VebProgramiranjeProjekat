$(document).ready(function(){
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
		data = {name: name, price: priceFloat, articleType: type, size: sizeInt, description: description, imageId: ""}
		$.ajax({
			url: "rest/ManagerService/addArticle",
			type: "PUT",
			contentType: "application/json",
			data: JSON.stringify(data),
			complete: function(message){
				if(message.responseText == "true"){
					alert("Article added!")
					window.location = "myRestaurant_manager.html";
				}else{
					alert("That article already exists!");
				}
			}
			
		})
	})
	
	
})
