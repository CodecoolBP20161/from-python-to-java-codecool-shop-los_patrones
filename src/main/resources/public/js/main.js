$(document).ready(function(){
    $('#resident_modal').on('show.bs.modal', function (event) {


        $.ajax({url: "/cart", success: function(result){
            console.log("Result: ");

        }});
    })
});