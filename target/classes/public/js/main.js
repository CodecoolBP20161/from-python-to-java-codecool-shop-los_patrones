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
                                "<td>" + result.quantites[i] + "</td>";
                                // "<td><span class='glyphicon glyphicon-plus'></span></td>" +
                                // "<td><span class='glyphicon glyphicon-minus'></span></td>";
                table_body.appendChild(row);

                var $button = $('<button/>', {
                    type: 'button',
                    'class': 'glyphicon glyphicon-plus',
                    id: result.names[i],
                    text: 'Here!',
                    click: function() {
                        window.alert('Hello! My id is '+ this.id);
                    }
                });

                $button.appendTo('#valami');
            }
        }});
    });
});