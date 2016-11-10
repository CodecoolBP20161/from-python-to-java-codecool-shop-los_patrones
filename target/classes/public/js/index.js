// controller objects
$('document').ready(function(){

var dataManager = {
    currentCategory: "all",
    currentSupplier: "all",
    allCategories: null,
    allSuppliers: null,
    allProducts: null,
    cart: null
}

var displayHandler = {

    renderSupplierBar: function(){
        var supplierBar = document.getElementById("searchSupplier");
        var supplier = dataManager.allSuppliers["Supplier"];
        for (i in supplier) {
            var option = document.createElement("OPTION");
            option.innerHTML = supplier[i]["name"];
            option.value = "supplier/" + supplier[i]["id"];
            supplierBar.appendChild(option);
        }
    },

    renderCategoryBar: function(){
        var categoryBar = document.getElementById("searchCategory");
        var categories = dataManager.allCategories["Category"];
        for (i in categories) {
            var option = document.createElement("OPTION");
            option.innerHTML = categories[i]["name"];
            option.value = "category/" + categories[i]["id"];
            categoryBar.appendChild(option);
        }
    },
    renderProducts: function () {
        var products = dataManager.allProducts["Products"];
        var container = document.getElementById("products");
        container.innerHTML = "";
        for (i in products) {
            var thumbnail = document.createElement("DIV");
            thumbnail.className = "item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail";
            var picture = document.createElement("IMG");
            picture.src = "http://placehold.it/400x250/000/fff";
            picture.className = "group list-group-image";
            var textField = document.createElement("DIV");
            textField.className = "caption";
            var title = document.createElement("H4");
            title.className = "group inner list-group-item-heading";
            title.innerHTML = products[i]["name"];
            var description = document.createElement("P");
            description.className = "group inner list-group-item-text";
            description.innerHTML = products[i]["description"];
            var footer = document.createElement("P");
            var price = document.createElement("SPAN");
            price.innerHTML = products[i]["price"] + " " + products[i]["currency"];
            var button = document.createElement("BUTTON");
            button.innerHTML = "Add to cart";
            button.className = "btn btn-success";
            button.id = "getProduct/" + products[i]["id"];
            footer.appendChild(price);
            footer.appendChild(button);
            textField.appendChild(title);
            textField.appendChild(description);
            textField.appendChild(footer);
            thumbnail.appendChild(picture);
            thumbnail.appendChild(textField);
            container.appendChild(thumbnail);
        }
    }
}

var apiHandler = {
    initApp: function () {
        var requestCategories = new XMLHttpRequest();
        var requestSuppliers = new XMLHttpRequest();
        requestCategories.open("GET", "/categories", false);
        requestSuppliers.open("GET", "/suppliers", false);

        requestCategories.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dataManager.allCategories = JSON.parse(this.responseText);
                displayHandler.renderCategoryBar();
            }
        };


        requestSuppliers.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dataManager.allSuppliers = JSON.parse(this.responseText);
                displayHandler.renderSupplierBar();
            }
        };
        requestCategories.send();
        requestSuppliers.send();
        this.getByParams();
    },
    getByParams: function () {
        var request = new XMLHttpRequest();
        var url = "/products";
        if (dataManager.currentSupplier != "all" || dataManager.currentCategory != "all") {
            url = url + "?";
        }
        if (dataManager.currentCategory != "all") {
            url = url + "category=" + dataManager.currentCategory;
        }
        if (dataManager.currentSupplier != "all") {
            if (dataManager.currentCategory != "all") { url = url + "&"}
            url = url + "supplier=" + dataManager.currentSupplier;
        }
        console.log(url);
        request.open("GET", url, true);
        request.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dataManager.allProducts = JSON.parse(this.responseText);
                displayHandler.renderProducts();
            }
        };
        request.send();
    },
    getProduct: function (productId) {

        var request = new XMLHttpRequest();
        var url = "/tocart";

        request.open("POST", url, true);
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify({id: productId}));
        request.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dataManager.cart = JSON.parse(this.responseText);
                console.log(dataManager.cart);
            }
        };
    },

    removeProduct: function(productId){

        var request = new XMLHttpRequest();
        var url = "/fromcart";

        request.open("POST", url, true);
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify({id: productId}));
        request.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dataManager.cart = JSON.parse(this.responseText);
                console.log(dataManager.cart);
            }
        };
    },
}

var inputHandler = {
    convertId: function (id) {
        return id.split("/");
    }
}


// init

apiHandler.initApp();


// event handling

document.body.addEventListener("click", function(event) {
    if (event.target.tagName == "SELECT") {
        if (inputHandler.convertId(event.target.value)[0] == "category") {
            dataManager.currentCategory = inputHandler.convertId(event.target.value)[1];
        }
        if (inputHandler.convertId(event.target.value)[0] == "supplier") {
            dataManager.currentSupplier = inputHandler.convertId(event.target.value)[1];
        }
        apiHandler.getByParams()
    }
    if (inputHandler.convertId(event.target.id)[0] == "getProduct") {
        console.log(inputHandler.convertId(event.target.id)[1]);
        console.log(typeof inputHandler.convertId(event.target.id)[1]);
        apiHandler.getProduct(inputHandler.convertId(event.target.id)[1]);
    }

    if(event.target.id[0] == '-'){
        console.log(globalresult.id[event.target.id[1]]);
        console.log(typeof globalresult.id[event.target.id[1]]);
        apiHandler.removeProduct(String(globalresult.id[event.target.id[1]]));
        refreshModal();
    }

    if(event.target.id[0] == '+'){
        apiHandler.getProduct(String(globalresult.id[event.target.id[1]]));
        refreshModal();
    }
})

var globalresult = "";

    function refreshModal(){
        $.ajax({url: "/cart", success: function(result){

            result = JSON.parse(result);
            globalresult = result;
            var table_body = document.getElementById("modal-tbody");
            table_body.innerHTML = "";

            for(var i = 0; i < result.prices.length; i++){

                var row = document.createElement("tr");
                row.innerHTML = "<td>" + result.names[i] + "</td>" +
                    "<td>" + result.prices[i]+"</td>" +
                    "<td id='quant'>" + result.quantites[i] + "</td>" +
                    "<td><button type='button' class='button glyphicon glyphicon-minus' id='-"+ i +"'></button></td>" +
                    "<td><button type='button' class='button glyphicon glyphicon-plus' id='+" + i + "'></button></td>";
                table_body.appendChild(row);

            }
        }});


    }

    $('#cartModalButton').click(function(){
        refreshModal();
    });

    // $('button').click(function(){
    //     if(this.id[0] == '-'){
    //         console.log("-");
    //         console.log(this.id);
    //         apiHandler.removeProduct(result.id[id]);
    //
    //     }else if(this.id[0] == '+'){
    //         console.log("+");
    //         console.log(this.id);
    //         apiHandler.getProduct(result.id[id]);
    //         // console.log($('#quant').html("cica"));
    //         console.log(result.quantites);
    //
    //     }
    // });
});
