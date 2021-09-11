$(document).ready(function() {
    $("#confirm").click(function(e) {
        e.preventDefault();
        let name = $("#name").val();
        let surname = $("#surname").val();
        let username = $("#username").val();
        let password = $("#password").val();
        let gender = $("#gender").val();
        let dateOfBirth = $("#date").val();
        let data = { name: name, surname: surname, username: username, password: password, gender: gender, dateOfBirth: dateOfBirth };
        if (name != "" && surname != "" && username != "" && dateOfBirth != "") {
            if (password.length > 7) {
                jsonData = JSON.stringify(data);
                $.ajax({
                    url: "rest/AdministratorService/createNewDeliverer",
                    type: 'PUT',
                    data: jsonData,
                    contentType: 'application/json',
                    complete: (function(message) {
                        if (message.responseText === "true") {
							alert("New deliverer created")
                            window.location = "administrator_deliverers.html";
                        }else{
							alert("Username not available");
						}
                    })
                });
            } else {
                alert("password must be longer than 7 characters");
            }
        } else {
            alert("fill in all fields");
        }
    })
})