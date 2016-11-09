$('document').ready(function(){

    $('#cartModalButton').click(function(){
        $.ajax({url: "/cart", success: function(result){

            result = JSON.parse(result);
            var table_body = document.getElementById("modal-tbody")
            table_body.innerHTML = "";

            for(var i = 0; i < result.prices.length; i++){

                var row = document.createElement("tr");
                row.id = "valami";
                row.innerHTML = "<td>" + result.names[i] + "</td>" +
                                "<td>" + result.prices[i]+"</td>" +
                                "<td>" + result.quantites[i] + "</td>" +
                                "<td><button type='button' class='button glyphicon glyphicon-minus' id='+"+ i +"'></button></td>" +
                                "<td><button type='button' class='button glyphicon glyphicon-plus' id='-" + i + "'></button></td>";
                table_body.appendChild(row);

            }
            $('button').click(function(){
                console.log(this.id);
            });
        }});

    });

});