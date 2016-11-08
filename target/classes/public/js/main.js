$('document').ready(function(){

    $('#cartModalButton').click(function(){
        $.ajax({url: "/cart", success: function(result){
            result = JSON.parse(result);
            console.log(result);

            var table_body = document.getElementById("cicamica")

            for(var i = 0; i < result.prices.length; i++){

                var row = document.createElement("tr");
                row.innerHTML = "<td>" + result.names[i] + "</td><td>" + result.prices[i]+"</td><td>" + result.quantites[i] + "</td>"
                table_body.appendChild(row);
                
            }
        }});
    });
});