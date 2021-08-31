$(document).ready(function() {
    $("#dugmic").click(function(e) {
        e.preventDefault();
        $.get({
            url: "rest/CustomerService/getCustomer",
            contentType: 'application/json',
            complete: function(message) {
                alert(message.responseText);
            }
        });
    })
})