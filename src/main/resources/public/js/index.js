// controller objects

var dataManager = {
    currentCategory: "all",
    currentSupplier: "all",
    allSearchParams: null,
    allProducts: null
}

var displayHandler = {
    renderSearchBar: function () {
        var categoryBar = document.getElementById("searchCategory");
        var categories = dataManager.allSearchParams["Category"];
        for (i in categories) {
            var option = document.createElement("OPTION");
            option.innerHTML = categories[i]["name"];
            option.value = "category/" + categories[i]["id"];
            categoryBar.appendChild(option);
        }
        var supplierBar = document.getElementById("searchSupplier");
        var supplier = dataManager.allSearchParams["Supplier"];
        for (i in supplier) {
            var option = document.createElement("OPTION");
            option.innerHTML = supplier[i]["name"];
            option.value = "supplier/" + supplier[i]["id"];
            supplierBar.appendChild(option);
        }
    },
    renderProducts: function () {
        var products = dataManager.allProducts["Products"];
        var container = document.getElementById("products");
        container.innerHTML = "";
        for (i in products) {
            var thumbnail = document.createElement("DIV");
            thumbnail.className = "item col-xs-4 col-sm-4 col-md-4 col-lg-4";
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
        var request = new XMLHttpRequest();
        request.open("GET", "http://127.0.0.1:8888/initMain", false);
        request.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dataManager.allSearchParams = JSON.parse(this.responseText);
                displayHandler.renderSearchBar();
            }
        };
        request.send();
        this.getByParams();
    },
    getByParams: function () {
        var request = new XMLHttpRequest();
        var url = "http://127.0.0.1:8888/indexSearch";
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
        var url = "http://127.0.0.1:8888/tocart";
        request.open("POST", url, true);
        request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        request.send(JSON.stringify({id:productId}));
    }
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
        idToSend = inputHandler.convertId(event.target.id)[1];
        console.log(idToSend);
        // apiHandler.getProduct(idToSend);
    }
})