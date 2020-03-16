$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8088/app/api/cross/for9000"
    }).then(function(data, status, log) {
        $('.domain').append(data.crossOriginDomain);
        $('.message').append(data.message);
        console.log(log);
    });
});