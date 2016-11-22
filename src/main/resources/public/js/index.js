// // controller objects
// $('document').ready(function(){
//
// // var dataManager = {
// //     currentCategory: "all",
// //     currentSupplier: "all",
// //     allCategories: null,
// //     allSuppliers: null,
// //     allProducts: null,
// //     cart: null
// // }
//
// var displayHandler = {
//
//     renderCartItems: function () {
//         var carticon = document.getElementById("items");
//         carticon.innerHTML = dataManager.cart.totalquantity[0];
//     },
//
//     renderSupplierBar: function(){
//         var supplierBar = document.getElementById("searchSupplier");
//         var supplier = dataManager.allSuppliers["Supplier"];
//         for (i in supplier) {
//             var option = document.createElement("OPTION");
//             option.innerHTML = supplier[i]["name"];
//             option.value = "supplier/" + supplier[i]["id"];
//             supplierBar.appendChild(option);
//         }
//     },
//
//     renderCategoryBar: function(){
//         var categoryBar = document.getElementById("searchCategory");
//         var categories = dataManager.allCategories["Category"];
//         for (i in categories) {
//             var option = document.createElement("OPTION");
//             option.innerHTML = categories[i]["name"];
//             option.value = "category/" + categories[i]["id"];
//             categoryBar.appendChild(option);
//         }
//     },
//     renderProducts: function () {
//         var products = dataManager.allProducts["Products"];
//         var container = document.getElementById("products");
//         container.innerHTML = "";
//
//         for (i in products) {
//             var thumbnail = document.createElement("DIV");
//             thumbnail.className = "item col-xs-12 col-sm-6 col-md-4 thumbnail";
//             var picture = document.createElement("IMG");
//             picture.src = "/img/product_" + products[i]["id"] + ".jpg";
//             picture.className = "group list-group-image";
//             var textField = document.createElement("DIV");
//             textField.className = "caption";
//             var title = document.createElement("H4");
//             title.className = "group inner list-group-item-heading";
//             title.innerHTML = products[i]["name"];
//             var description = document.createElement("P");
//             description.className = "group inner list-group-item-text";
//             description.innerHTML = products[i]["description"];
//             var footer = document.createElement("P");
//             var price = document.createElement("SPAN");
//             price.innerHTML = products[i]["price"] + " " + products[i]["currency"];
//             var button = document.createElement("BUTTON");
//             button.innerHTML = "Add to cart";
//             button.className = "btn btn-success";
//             button.id = "getProduct/" + products[i]["id"];
//             footer.appendChild(price);
//             footer.appendChild(button);
//             textField.appendChild(title);
//             textField.appendChild(description);
//             textField.appendChild(footer);
//             thumbnail.appendChild(picture);
//             thumbnail.appendChild(textField);
//             container.appendChild(thumbnail);
//         }
//     }
// };
//
// var apiHandler = {
//     sendCheckout : function (data) {
//         var request = new XMLHttpRequest();
//         var url = "/createOrder";
//
//         request.open("POST", url, true);
//         request.setRequestHeader("Content-Type", "application/json");
//         request.send(data);
//         window.location.href="/pay";
//
//     },
//
//     initApp: function () {
//         var requestCategories = new XMLHttpRequest();
//         var requestSuppliers = new XMLHttpRequest();
//         var requestCart = new XMLHttpRequest();
//         requestCategories.open("GET", "/categories", false);
//         requestSuppliers.open("GET", "/suppliers", false);
//         requestCart.open("GET", "/cart", false);
//
//         requestCategories.onreadystatechange = function () {
//             if (this.readyState == 4 && this.status == 200) {
//                 dataManager.allCategories = JSON.parse(this.responseText);
//                 displayHandler.renderCategoryBar();
//             }
//         };
//
//
//         requestSuppliers.onreadystatechange = function () {
//             if (this.readyState == 4 && this.status == 200) {
//                 dataManager.allSuppliers = JSON.parse(this.responseText);
//                 displayHandler.renderSupplierBar();
//             }
//         };
//
//         requestCart.onreadystatechange = function () {
//             if (this.readyState == 4 && this.status == 200) {
//                 dataManager.cart = JSON.parse(this.responseText);
//                 displayHandler.renderCartItems();
//             }
//         };
//         requestCategories.send();
//         requestSuppliers.send();
//         requestCart.send();
//         this.getByParams();
//     },
//     getByParams: function () {
//         var request = new XMLHttpRequest();
//         var url = "/products";
//         if (dataManager.currentSupplier != "all" || dataManager.currentCategory != "all") {
//             url = url + "?";
//         }
//         if (dataManager.currentCategory != "all") {
//             url = url + "category=" + dataManager.currentCategory;
//         }
//         if (dataManager.currentSupplier != "all") {
//             if (dataManager.currentCategory != "all") { url = url + "&"}
//             url = url + "supplier=" + dataManager.currentSupplier;
//         }
//         console.log(url);
//         request.open("GET", url, true);
//         request.onreadystatechange = function () {
//             if (this.readyState == 4 && this.status == 200) {
//                 dataManager.allProducts = JSON.parse(this.responseText);
//                 displayHandler.renderProducts();
//             }
//         };
//         request.send();
//     },
//     getProduct: function (productId) {
//
//         var request = new XMLHttpRequest();
//         var url = "/tocart";
//
//         request.open("POST", url, true);
//         request.setRequestHeader("Content-Type", "application/json");
//         request.send(JSON.stringify({id: productId}));
//         request.onreadystatechange = function () {
//             if (this.readyState == 4 && this.status == 200) {
//                 dataManager.cart = JSON.parse(this.responseText);
//                 console.log(dataManager.cart);
//                 displayHandler.renderCartItems();
//             }
//         };
//     },
//
//     removeProduct: function(productId){
//
//         var request = new XMLHttpRequest();
//         var url = "/fromcart";
//
//         request.open("POST", url, true);
//         request.setRequestHeader("Content-Type", "application/json");
//         request.send(JSON.stringify({id: productId}));
//         request.onreadystatechange = function () {
//             if (this.readyState == 4 && this.status == 200) {
//                 dataManager.cart = JSON.parse(this.responseText);
//                 console.log(dataManager.cart);
//                 displayHandler.renderCartItems();
//             }
//         };
//     }
// };
//
// var inputHandler = {
//     convertId: function (id) {
//         return id.split("/");
//     }
// };
//
//
// // init
//
// apiHandler.initApp();
//
//
// // event handling
//
// document.body.addEventListener("click", function(event) {
//     if (event.target.tagName == "SELECT") {
//         if (inputHandler.convertId(event.target.value)[0] == "category") {
//             dataManager.currentCategory = inputHandler.convertId(event.target.value)[1];
//         }
//         if (inputHandler.convertId(event.target.value)[0] == "supplier") {
//             dataManager.currentSupplier = inputHandler.convertId(event.target.value)[1];
//         }
//         apiHandler.getByParams()
//     }
//     if (inputHandler.convertId(event.target.id)[0] == "getProduct") {
//         console.log(inputHandler.convertId(event.target.id)[1]);
//         console.log(typeof inputHandler.convertId(event.target.id)[1]);
//         apiHandler.getProduct(inputHandler.convertId(event.target.id)[1]);
//     }
//
//
//     if(event.target.id[0] == '-'){
//         console.log(globalresult.id[event.target.id[1]]);
//         console.log(typeof globalresult.id[event.target.id[1]]);
//         apiHandler.removeProduct(String(globalresult.id[event.target.id[1]]));
//         refreshModal();
//     }
//
//     if(event.target.id[0] == '+'){
//         apiHandler.getProduct(String(globalresult.id[event.target.id[1]]));
//         refreshModal();
//     }
//     if(event.target.id == 'checkoutstart') {
//         console.log(dataManager.cart.prices)
//         if (dataManager.cart.prices.length > 0) {
//             $('#cartModal').modal('hide');
//             $('#checkoutModal').modal('show');
//         }
//     }
//
//     if(event.target.id == 'pay'){
//         console.log(dataManager.cart);
//         var list = checkInput.checkform();
//         if (list.length == 12) {
//             var returnData = {
//                 firstName : list[0],
//                 lastName : list[1],
//                 email : list[2],
//                 phoneNumber : list[3],
//                 billingCountry : list[4],
//                 billingCity : list[5],
//                 billingZip : list[6],
//                 billingAddress : list[7],
//                 shippingCountry : list[8],
//                 shippingCity : list[9],
//                 shippingZip : list[10],
//                 shippingAddress : list[11],
//                 id: dataManager.cart.id[0]
//             }
//             apiHandler.sendCheckout(JSON.stringify(returnData));
//         }
//
//     }
// });
//
var globalresult = "";

    function refreshModal(){
        $.ajax({url: "/cart", success: function(result){

            result = JSON.parse(result);
            globalresult = result;
            var table_body = document.getElementById("modal-tbody");
            var total_price = document.getElementById("totalPrice");
            total_price.innerHTML = "";
            table_body.innerHTML = "";
            total_price.innerHTML = result.totalprice[0] + " USD";
            displayHandler.renderCartItems();

            for(var i = 0; i < result.prices.length; i++){

                var row = document.createElement("tr");
                row.innerHTML = "<td>" + result.names[i] + "</td>" +
                    "<td>" + result.quantites[i]+"</td>" +
                    "<td id='quant'>" + result.prices[i] + "</td>" +
                    "<td><button type='button' class='button glyphicon glyphicon-minus' id='-"+ i +"'></button></td>" +
                    "<td><button type='button' class='button glyphicon glyphicon-plus' id='+" + i + "'></button></td>";
                table_body.appendChild(row);

            }
        }});


    }

    $('#cartModalButton').click(function(){
        refreshModal();
    });
// });
