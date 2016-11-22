function Model(cart, categories, suppliers, products){

    this.cart = cart;
    this.categories = categories;
    this.suppliers = suppliers;
    this.products = products;
}

var controller = {
    initApp: function(){
        var getCartData = $.ajax({ url: '/cart' }),
            getCategoryData = $.ajax({ url: '/categories' }),
            getSupplierData = $.ajax({ url: '/suppliers'}),
            getProducts = $.ajax({url: '/products'});

        $.when(getCartData, getCategoryData, getSupplierData, getProducts
        ).done( function( cart, categories, suppliers, products ) {

            var model = new Model(cart, JSON.parse(categories[0]), JSON.parse(suppliers[0]), JSON.parse(products[0]));
            React.render(<ProductComponent data={model.products}/>, document.getElementById('products'));
            React.render(<SupplierBarComponent data={model.suppliers}/>, document.getElementById('searchSupplier'));
            React.render(<CategoryBarComponent data={model.categories}/>, document.getElementById('searchCategory'));


        });
    },
    selectSupplier: function(){
        console.log("selectSupplier");
    }
};

$('#searchSupplier').change(function() {
    var val = $('#searchSupplier').find('option:selected').text();
    console.log(val);
});

$('#searchCategory').change(function() {
    var val = $('#searchCategory').find('option:selected').text();
    console.log(val);
});

$('#products').on('click', 'button', function(event) {
    console.log(event.target.id);
});

$('#cartModal').on('click', 'button', function(event) {
    console.log(event.target.id);
});





controller.initApp();
