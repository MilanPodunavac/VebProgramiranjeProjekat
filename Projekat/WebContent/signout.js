$(document).ready(function() {
    $("#signout").click(function(e) {
        e.preventDefault();
        $.post({
            url: "rest/logout",
            complete: function() {
                window.location.replace("login.html");
            }
        })
    })
})