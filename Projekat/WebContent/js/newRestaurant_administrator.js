
function encodeImageFileAsURL(){

    var filesSelected = document.getElementById("inputFile").files;
    if (filesSelected.length > 0) {
      var fileToLoad = filesSelected[0];

      var fileReader = new FileReader();

      fileReader.onload = function(fileLoadedEvent) {
        var srcData = fileLoadedEvent.target.result; // <--- data: base64

        var newImage = document.createElement('img');
        newImage.src = srcData;

		newImage.style.width = '300px';
		newImage.style.height = 'auto'; 

        document.getElementById("imgPreview").innerHTML = newImage.outerHTML;
		     
      }
      fileReader.readAsDataURL(fileToLoad);
    }
}

$(document).ready(function(){
	$.get({
		url: "rest/AdministratorService/getManagersWithoutRestaurant",
		complete: function(message){
			let managers = JSON.parse(message.responseText);
			
			let managerSelect = document.getElementById("managers");
			for(let manager of managers){
				var option = document.createElement('option');
			    option.value = manager.username;
			    option.innerHTML = manager.name + " " + manager.surname;
			    managerSelect.appendChild(option);
			}
			if(managers.length == 0){
				document.getElementById("confirm").disabled = true;
			}
		}
	})
	
	$("#createManager").click(function(){
		window.location = "createManager_administrator.html?newRestaurant_administrator.html";
	})
	
	$("#confirm").click(function(){
		let name = $("#name").val();
        let type = $("#type").val();
		
		
        let latitudeVal = $("#latitude").val();
		let latitude = parseFloat(latitudeVal);
		if(!latitude){
			latitude = 0;
		}
		
		let longitudeVal = $("#longitude").val();
		let longitude = parseFloat(longitudeVal);
		if(!longitude){
			longitude = 0;
		}
		
        let streetName = $("#streetName").val();

		let streetNumberVal = $("#streetNumber").val();
		let streetNumber = parseFloat(streetNumberVal);
		if(!streetNumber){
			streetNumber = 0;
		}

		let cityName = $("#cityName").val();
		
		let cityNumberVal = $("#cityNumber").val();
		let cityNumber = parseFloat(cityNumberVal);
		if(!cityNumber){
			cityNumber = 0;
		}
		
		let managerUsername = $("#managers").val();
		var manager;
		$.get({
			url: "rest/ManagerService/getManagerByUsername?username=" + managerUsername,
			complete: function(message){
				manager = JSON.parse(message.responseText);
				
				let logoData = document.getElementById("imgPreview").innerHTML;
		
				var logoId = "";
				
				if(logoData != ""){
					$.post({
						url: "rest/Image64Service/addImage",
						data: JSON.stringify({id: "0", base64Data: logoData}),
						contentType: 'application/json',
						complete: function(message){
							logoId = message.responseText;
							
							let location = {longitude: longitude, latitude: latitude, cityName: cityName, cityNumber: cityNumber, streetName: streetName, streetNumber: streetNumber};
							let restaurant = {name: name, restaurantType: type, imageId: logoId, location: location}
							$.ajax({
								type: "PUT",
								url: "rest/AdministratorService/createNewRestaurant",
								data: JSON.stringify({manager: manager, restaurant: restaurant}),
								contentType: 'application/json',
								complete: function(){
									alert("Restaurant successfully created");
									window.location = "administrator.html";
								}
								
							})
						}
					})
				}
				
				
			}
		})


		
		
		
		
	})
})