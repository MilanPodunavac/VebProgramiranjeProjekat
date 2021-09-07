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
        let confirm = $("#passwordConfirm").val();
        if (name != "" && surname != "" && username != "" && dateOfBirth != "") {
            if (password == confirm && password.length > 7) {
                jsonData = JSON.stringify(data);
                $.ajax({
                    url: "rest/register",
                    type: 'PUT',
                    data: jsonData,
                    contentType: 'application/json',
                    complete: (function(message) {
                        alert(message.responseText);
                        if (message.responseText == "You have been successfully registered") {
                            window.location.href = "login.html";
                        }
                    })
                });
            } else {
                alert("password must be longer than 7 characters and you must match it in confirm password field");
            }
        } else {
            alert("fill in all fields");
        }
    })
})