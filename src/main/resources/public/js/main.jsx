/**
 * @fileOverview Front-end application of our Codecool Shop. It contains model, view and controller logic and is<br>
 *     self-contained except for rendered React components.
 *@requires react.js and JSXTransformer.js (from cdn in our case) and components.jxs
 */


/**
 * Stores the data needed by the client side to render the pages.
 */
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


/**
 * Handles all operations related to changing the look of a rendered page.
 */
var view = {
    refreshView: function(){
        this.refreshModal();
        this.refreshNumberOfItemsinCart();
        this.refreshTotalPrice();
    },

    refreshModal: function() {
        React.render(<CartComponent data={model.cart}/>, document.getElementById('modal-table'));
    },

    refreshNumberOfItemsinCart: function(){
        document.getElementById("numberOfItems").innerHTML = model.totalQuantity.toString();
    },

    refreshTotalPrice: function(){
        document.getElementById("totalPrice").innerHTML = model.totalPrice.toString() + "USD";
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


/**
 * Does the necessary calculations and handles AJAX requests and updates the model class with the results.
 */
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
            model.cart = JSON.parse(cart).products;
            model.totalQuantity = JSON.parse(cart).totalQuantity;
            model.totalPrice = JSON.parse(cart).totalPrice;
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

/**
 * Contains jquery event listeners.
 */
$(function(){
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
});

/** Main logic starts here.*/
controller.initApp();