$(document).ready(function(){
	$.get({
		url: "rest/currentUser",
        contentType: 'application/json',
		complete: function(message){
			let username_label = document.getElementById("username_label");
			let user = JSON.parse(message.responseText);
			username_label.innerText = user.username;
		}
	});
})