$(document).ready(function() {
    $("#signout").click(function(e) {
        e.preventDefault();
        $.post({
            url: "rest/logout",
            complete: function() {
                window.location.replace("index.html");
            }
        })
    })
})