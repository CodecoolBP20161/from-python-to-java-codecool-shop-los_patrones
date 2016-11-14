$('document').ready(function(){
    // datamanager
    // displayhandler
    // apihandler
    // inputhandler

    // addeventlisteners
    // globalresult globális változó
    // refreshmodal függvény

    // new model létrehozása

    function Model(){
        this.currentCategory = "all";
        this.currentSupplier = "all";
        this.allCategories = null;
        this.allSuppliers = null;
        this.allProducts = null;
        this.cart = null;
    }

    function View(){

        this.renderCartItems = function(){
            var cartIcon = document.getElementById("numberOfItems");
            cartIcon.innerHTML = model.cart.totalquantity[0];
        };

        this.renderSupplierBar = function(){
            var supplierBar = document.getElementById("searchSupplier");
            var supplier = model.allSuppliers["Supplier"];
            for(var i in supplier){
                var option = document.createElement("OPTION");
                option.innerHTML = supplier[i]["name"];
                option.value = "supplier/" + supplier[i]["id"];
                supplierBar.appendChild(option);
            }
        };

        this.renderCategoryBar = function(){
            var categoryBar = document.getElementById("searchCategory");
            var categories = model.allCategories["Category"];
            for(var i in categories){
                var option = document.createElement("OPTION");
                option.innerHTML = categories[i]["name"];
                option.value = "category/" + categories[i]["id"];
                categoryBar.appendChild(option);
            }
        };

        // listába megadni, hogy mi és hova megy
        this.renderProducts = function(){
            var prodcuts = model.allProducts["Products"];
            var container = document.getElementById("products");
            container.innerHTML = "";
            for (i in products) {
                var thumbnail = document.createElement("DIV");
                thumbnail.className = "item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail";
                var picture = document.createElement("IMG");
                picture.src = "/img/product_" + products[i]["id"] + ".jpg";
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
        };
    }


    function Controller(){
        this.requestCategories = new XMLHttpRequest();
        this.requestSuppliers = new XMLHttpRequest();
        this.requestCart = new XMLHttpRequest();
        this.requestCategories.open("GET", "/categories", false);
        this.requestSuppliers.open("GET", "/suppliers", false);
        this.requestCart.open("GET", "/cart", false);
    }


});

var apiHandler = {
    initApp: function () {
        var requestCategories = new XMLHttpRequest();
        var requestSuppliers = new XMLHttpRequest();
        var requestCart = new XMLHttpRequest();
        requestCategories.open("GET", "/categories", false);
        requestSuppliers.open("GET", "/suppliers", false);
        requestCart.open("GET", "/cart", false);

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

        requestCart.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dataManager.cart = JSON.parse(this.responseText);
                displayHandler.renderCartItems();
            }
        };
        requestCategories.send();
        requestSuppliers.send();
        requestCart.send();
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
                displayHandler.renderCartItems();
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
                displayHandler.renderCartItems();
            }
        };
    }
}