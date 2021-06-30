$(document).ready(function() {
    $("#login").click(function(e) {
        e.preventDefault();
        let username = $("#username").val();
        let password = $("#password").val();
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/Projekat/rest/login",
            data: { username: username, password: password },
            dataType: JSON,
            success: function(response) {
                if (response == null) {
                    alert("Wrong username or password!");
                }
            }

        })

    })
});