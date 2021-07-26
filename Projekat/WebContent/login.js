$(document).ready(function() {
    $("#login").click(function(e) {
        e.preventDefault();
        let username = $("#username").val();
        let password = $("#password").val();
        $.post({
            url: "rest/login",
            data: JSON.stringify({ username: username, password: password }),
            contentType: 'application/json',
            complete: function(message) {
                if (message.responseText == "customer") {
                    window.location.replace("customer.html");
                } else if (message.responseText == "deliverer") {
                    window.location.replace("deliverer.html");
                } else if (message.responseText == "manager") {
                    window.location.replace("manager.html");
                } else if (message.responseText == "administrator") {
                    window.location.replace("administrator.html");
                } else {
                    $("#username").val("");
                    $("#password").val("");
                    alert(message.responseText);
                }
            }
        })

    })
});