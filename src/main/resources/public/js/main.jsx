function Model(cart, categories, suppliers, products){

    this.cart = cart;
    this.categories = categories;
    this.suppliers = suppliers;
    this.products = products['Products'];
}

var controller = {
    initApp: function(){
        var getCartData = $.ajax({ url: '/cart' }),
            getCategoryData = $.ajax({ url: '/categories' }),
            getSupplierData = $.ajax({ url: '/suppliers'}),
            getProdcuts = $.ajax({url: '/products'});

        // Main controller logic starts when all data are loaded in
        $.when(getCartData, getCategoryData, getSupplierData, getProdcuts
        ).done( function( cart, categories, suppliers, products ) {
            var model = new Model(cart, categories, suppliers, JSON.parse(products[0]));

            console.log(model.products);
            console.log(typeof model.products);

            for(var i = 0; i<model.products.length; i++){
                console.log(model.products[i]);
                // ProductComponent.product = product;
                // console.log(ProductComponent.product);
                React.render(<ProductComponent level={3}/>, document.getElementById('products'));
            }

            // for(product in model.products){
            //     console.log(product);
            // }

        });
    }
};

var ProductComponent = React.createClass({
    render: function() {

        var tmp = [];
        for (var i = 0; i < this.props.level; i++) {
            tmp.push(i);
        }

        return (
            <div className="item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail">
                <img className="group list-group-image" src="" />
                <div className="caption">
                    <h4 className="group inner list-group-item-heading">cica név</h4>
                    <p className="group inner list-group-item-text">cica leírás</p>
                    <p>
                        <span>cica ár és currency</span>
                        <span>{this.props.firstName}</span>
                        <button id="'getProduct/' + {product.id}" className="btn btn-success">Add to cart</button>
                    </p>
                </div>
            </div>
        );
    }
});

// var CategoryBar = React.createClass({
//     render: function(){
//         return (
//             <option>{categories[i]['name']}</option>
//             // option value = categories[i]['id]
//         );
//     }
// });

// var SupplierBar = React.createClass({
//     render: function(){
//         return (
//
//         );
//     }
// });

controller.initApp();


















// var BuckysComponent = React.createClass({
//     render: function() {
//         return (
//             <h2>Próba</h2>
//         );
//     }
// });
//
// // React.render(<BuckysComponent />, document.getElementById('content'));
//
// // Model
// // View
// // Controller
// var model = new Model();
// console.log(model.cart);
//
// // function getReady(){
// //     var deferredReady = $.Deferred();
// //     $(document).ready(function() {
// //         deferredReady.resolve();
// //     });
// //     return deferredReady.promise();
// // }
//
//
// function Model(){
//     this.cart = "cica";
//     this.categories = null;
//     this.suppliers = null;
//
//     $.when(this.getCart, this.getCategories, this.getSuppliers).done(function(cart, categories, suppliers){
//         console.log(this.cart);
//     });
//
//     this.getCart = $.ajax({
//         url: '/cart',
//         dataType: 'json',
//     });
//
//     this.getCategories = $.ajax({
//         url: '/categories',
//         dataType: 'json'
//     });
//
//     this.getSuppliers = $.ajax({
//         url: '/suppliers',
//         dataType: 'json'
//     });
// }
//
//
// function View(){
//     this.renderCategoryBar = function(categories){
//         console.log(categories);
//     }
// }
//
// // var req = $.ajax({
// //     url: '/cart',
// //     dataType: 'json'
// // });
//
// // var success = function(response, status, alma){
// //     console.log(response);
// //     console.log(status);
// //     console.log(alma);
// //
// // };
// //
// // var error = function(request, status, error){
// //     console.log("Something went wrong!");
// //     console.log(request);
// //     console.log(status);
// //     console.log(error);
// // };
// //
// // req.then(success, error);
// // // then: first in, first out queue
// // // req.done() és req.fail() is lehetőség
// // // req.always() mindig meghívódik
//
