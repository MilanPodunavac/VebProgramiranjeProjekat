$(document).ready(function(){
	$.get({
		url: "rest/ManagerService/getManager",
		contentType: 'application/json',
		complete: function(message){
			manager = JSON.parse(message.responseText);
			if(manager.restaurant == null){
				alert("Sorry, you have no restaurant");
				window.location = "manager.html";
			}
			$.get({
				url: "rest/ManagerService/getNotApprovedRestaurantComments",
				contentType: 'application/json',
				complete: function(message){
					comments = JSON.parse(message.responseText);
					comment_table = document.getElementById("comment_table");
								
					for(let comment of comments){
						let commentTr = document.createElement("tr");
						
						let customerTd = document.createElement("td");
						let gradeTd = document.createElement("td");
						let textTd = document.createElement("td");
						let buttonTd = document.createElement("td");
						let button = document.createElement("button");
						
						customerTd.appendChild(document.createTextNode(comment.customer.name + " " + comment.customer.surname));
						gradeTd.appendChild(document.createTextNode(comment.grade));
						textTd.appendChild(document.createTextNode(comment.text));
						
						button.appendChild(document.createTextNode("Approve"));
						$(button).click(function(){
							$.post({
								url: "rest/ManagerService/approveComment",
								contentType: "application/json",
								data: JSON.stringify(comment),
								complete: function(message){
									let rows = comment_table.rows;
									name = comment.customer.name + " " + comment.customer.surname;
									grade = comment.grade;
									text = comment.text;
									for(let i = 1; i<rows.length; i++){
										if(name == rows[i].cells[0].innerText && grade == rows[i].cells[1].innerText && rows[i].cells[2].innerText == text){
											comment_table.deleteRow(i);
											break;
										}
									}
								}
							})
						})
						
						let rejectButton = document.createElement("button");
						rejectButton.appendChild(document.createTextNode("Reject"));
						
						$(rejectButton).click(function(){
							$.post({
								url: "rest/ManagerService/rejectComment",
								contentType: "application/json",
								data: JSON.stringify(comment),
								complete: function(message){
									let rows = comment_table.rows;
									name = comment.customer.name + " " + comment.customer.surname;
									grade = comment.grade;
									text = comment.text;
									for(let i = 1; i<rows.length; i++){
										if(name == rows[i].cells[0].innerText && grade == rows[i].cells[1].innerText && rows[i].cells[2].innerText == text){
											comment_table.deleteRow(i);
											break;
										}
									}
								}
							})
						})
						
						buttonTd.appendChild(button);
						buttonTd.appendChild(rejectButton);
						
						commentTr.appendChild(customerTd);
						commentTr.appendChild(gradeTd);
						commentTr.appendChild(textTd);
						commentTr.appendChild(buttonTd);
						
						comment_table.appendChild(commentTr);
					}
				}
				
			})
		}
	})
})