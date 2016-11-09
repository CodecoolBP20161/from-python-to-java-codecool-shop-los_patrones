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
        for (i in products) {
            console.log(products[i]);
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
})
