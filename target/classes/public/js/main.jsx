var model = {
    cart : null,
    categories : null,
    suppliers : null,
    products : null,
    totalPrice: 0,
    totalQuantity: 0,
    currentCategory: 'All categories',
    currentSupplier: 'All suppliers',

    initModel: function(cart, categories, suppliers, products){
        this.cart = cart;
        this.categories = categories;
        this.suppliers = suppliers;
        this.products = products;
    }
};

var view = {
    refreshView: function(){
        this.refreshModal();
        this.refreshNumberOfItemsinCart();
    },

    refreshModal: function() {
        React.render(<CartComponent data={model.cart}/>, document.getElementById('modal-table'));
    },

    refreshNumberOfItemsinCart: function(){
        document.getElementById("numberOfItems").innerHTML = model.totalQuantity.toString();
    },

    renderProducts: function(){
        var data = controller.selectCategoryData(model.products);
        data = controller.selectSupplierData(data);
        React.render(<ProductComponent data={data}/>, document.getElementById('products'));
    },

    renderOptionBars: function() {
        React.render(<SupplierBarComponent data={model.suppliers}/>, document.getElementById('searchSupplier'));
        React.render(<CategoryBarComponent data={model.categories}/>, document.getElementById('searchCategory'));
    }
};


var controller = {
    initApp: function(){
        var getCartData = $.ajax({ url: '/cart' }),
            getCategoryData = $.ajax({ url: '/categories' }),
            getSupplierData = $.ajax({ url: '/suppliers'}),
            getProducts = $.ajax({url: '/products'});

        $.when(getCartData, getCategoryData, getSupplierData, getProducts
        ).done( function( cart, categories, suppliers, products ) {

            model.initModel(JSON.parse(cart[0]), JSON.parse(categories[0]), JSON.parse(suppliers[0]), JSON.parse(products[0]));
            view.renderProducts();
            view.renderOptionBars();
        });
    },

    selectSupplierData: function(products){
        if(model.currentSupplier == 'All suppliers'){
            return products;
        }

        var result = [];

        for(var i = 0; i<products.length; i++){
            if(model.currentSupplier == products[i].supplier){
                result.push(products[i]);
            }
        }

        return result;
    },

    selectCategoryData: function(products){
        if(model.currentCategory == 'All categories'){
            return products;
        }

        var result = [];
        
        for(var i = 0; i<products.length; i++){
            if(model.currentCategory == products[i].category){
                result.push(products[i]);
            }
        }
        return result;

    },

    refreshCartContent: function(){
        $.when($.ajax({ url: '/cart' })).done(function(cart){
            console.log(cart);
            console.log(typeof cart);
            model.cart = JSON.parse(cart).products;
            model.totalQuantity = model.cart.length;
            view.refreshView();
        })
    },

    putToCart: function(id) {
        var updateMessage = {
            id: id,
            method: 'add'
        };
        $.when($.ajax({url: '/updatecart', type: 'POST', data: JSON.stringify(updateMessage)})).done(function(){
            controller.refreshCartContent();
        })
    },

    removeFromCart: function(id) {
        var updateMessage = {
            id: id,
            method: 'remove'
        };
        $.when($.ajax({url: '/updatecart', type: 'POST', data: JSON.stringify(updateMessage)})).done(function(){
            controller.refreshCartContent();
        })
    }
};

$('#searchSupplier').change(function() {
    model.currentSupplier = $('#searchSupplier').find('option:selected').text();
    view.renderProducts();
});

$('#searchCategory').change(function() {
    model.currentCategory = $('#searchCategory').find('option:selected').text();
    view.renderProducts();
});

$('#products').on('click', 'button', function(event) {
    controller.putToCart(event.target.id);
});


$('#cartModalButton').click(function(){
    view.refreshView();
});

$('#cartModal').on('click', 'button', function(event) {

    if(event.target.className.includes('plus')){
        controller.putToCart(event.target.id);
    }else if(event.target.className.includes('minus')){
        controller.removeFromCart(event.target.id);
    }
});







controller.initApp();
