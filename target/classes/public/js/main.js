$(document).ready(function () {

    $.ajax({
        type: 'GET',
        url: '/hello',
        success: function (data) {
            console.log('success', data);
        }
    });

});