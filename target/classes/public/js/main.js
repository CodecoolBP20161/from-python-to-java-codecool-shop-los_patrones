$('document').ready(function(){
    $.ajax({url: "/cart", success: function(result){
        // $("#div1").html(result);
        console.log("Result: ");
        console.log(JSON.parse(result));

    }});
});