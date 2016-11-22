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
            var model = new Model(cart, categories, JSON.parse(suppliers[0]), JSON.parse(products[0]));
            React.render(<ProductComponent data={model}/>, document.getElementById('products'));
            React.render(<SupplierBarComponent data={model.suppliers}/>, document.getElementById('searchSupplier'));
            React.render(<CategoryBarComponent data={model.categories}/>, document.getElementById('searchCategory'));


        });
    }
};

var SupplierBarComponent = React.createClass({
    render: function() {
        const supplierData = this.props.data;
        console.log(supplierData);
        console.log(typeof supplierData);


        const suppliers = supplierData.map(function(supplier){
            return (
                <option>{supplier.name}</option>
            );
        });

        return(
            <div>{suppliers}</div>
        );
    }
});

var CategoryBarComponent = React.createClass({
    render: function() {
        return(
            <p>mica</p>
        );

    }
});

var ProductComponent = React.createClass({
    render: function() {

        const products = this.props.data.products;
        console.log(products);
        const currentProducts = products.map(function(product){
            return(
                <div className="item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail">
                    <img className="group list-group-image" src={"/img/product_" + product.id + ".jpg"}/>
                    <div className="caption">
                        <h4 className="group inner list-group-item-heading">{product.name}</h4>
                        <p className="group inner list-group-item-text">{product.description}</p>
                        <p>
                            <span>{product.price} {product.currency}</span>
                            <button id={"getProduct/" + product.id} className="btn btn-success">Add to cart</button>
                        </p>
                    </div>
                </div>
            );
        });

        return (
            <div>{currentProducts}</div>
        );
    }
});


controller.initApp();
