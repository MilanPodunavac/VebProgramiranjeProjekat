$(document).ready(function(){
	$.get({
		url: "rest/AdministratorService/getAllDeliverers",
		contetType: 'application/json',
		complete: function(message){
			deliverers = JSON.parse(message.responseText);
			deliverer_list = document.getElementById("deliverer_list");
			for(deliverer of deliverers){
				let delivererTr = document.createElement('tr');
				let usernameTd = document.createElement('td');
				let nameTd = document.createElement('td');
				let surnameTd = document.createElement('td');
				let genderTd = document.createElement('td');
				let dateOfBirthTd = document.createElement('td');
				
				usernameTd.appendChild(document.createTextNode(deliverer.username));
				nameTd.appendChild(document.createTextNode(deliverer.name));
				surnameTd.appendChild(document.createTextNode(deliverer.surname));
				genderTd.appendChild(document.createTextNode(deliverer.gender));
				let date = new Date(parseInt(deliverer.dateOfBirth));
				dateOfBirthTd.appendChild(document.createTextNode(date.toLocaleString()));
				
				delivererTr.appendChild(usernameTd);
				delivererTr.appendChild(nameTd);
				delivererTr.appendChild(surnameTd);
				delivererTr.appendChild(genderTd);
				delivererTr.appendChild(dateOfBirthTd);
				
				deliverer_list.appendChild(delivererTr);
			}
		}
	})
	
	$("#deliverer_list").on("click", "tr", function(){
		let deliverer_username = $(this).children("td:first").text();
		if(deliverer_username == "")return;
		window.location = "delivererView_administrator.html?username=" + deliverer_username;
	})
	$("#createDeliverer").click(function(){
		window.location = "createDeliverer_administrator.html";
	})
	
})

