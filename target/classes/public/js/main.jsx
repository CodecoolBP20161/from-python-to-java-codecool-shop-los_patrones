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
            React.render(<ProductComponent data={model}/>, document.getElementById('products'));


        });
    }
};

var ProductComponent = React.createClass({
    render: function() {

        const products = this.props.data.products;

        // const currentProducts = (
        //     <div>cica</div>
        // );

        const currentProducts = products.map(function(product){
            return(
                <div className="item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail">
                    <img className="group list-group-image" src=""/>
                    <div className="caption">
                        <h4 className="group inner list-group-item-heading">{product.name}</h4>
                        <p className="group inner list-group-item-text">{product.description}</p>
                        <p>
                            <span>{product.price} {product.currency}</span>
                            <button id="d" className="btn btn-success">Add to cart</button>
                        </p>
                    </div>
                </div>
            );
        });


        return (
            <div>{currentProducts}</div>
        );

        // const renderProducts = products => (
        //     products.map(product => renderProduct(product))
        // );
        //
        // const renderProduct = product => (
        //     <div className="item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail">
        //         <img className="group list-group-image" src=""/>
        //         <div className="caption">
        //             <h4 className="group inner list-group-item-heading">cica név</h4>
        //             <p className="group inner list-group-item-text">cica leírás</p>
        //             <p>
        //                 <span>cica ár és currency</span>
        //                 <span>nemtommi</span>
        //                 <span>{product}</span>
        //                 <button id="'getProduct/' + {product.id}" className="btn btn-success">Add to cart</button>
        //             </p>
        //         </div>
        //     </div>
        // );

        // const renderProduct = product => (
        //     <div className="item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail">
        //         <img className="group list-group-image" src=""/>
        //         <div className="caption">
        //             <h4 className="group inner list-group-item-heading">cica név</h4>
        //             <p className="group inner list-group-item-text">cica leírás</p>
        //             <p>
        //                 <span>cica ár és currency</span>
        //                 <span>nemtommi</span>
        //                 <span>{product}</span>
        //                 <button id="'getProduct/' + {product.id}" className="btn btn-success">Add to cart</button>
        //             </p>
        //         </div>
        //     </div>
        // );
        //
        // const renderProducts = products => (
        //     products.map(product => renderProduct(product))
        // );
        //
        // const currentProducts = props => {
        //     const products = renderProducts(props.data.products);
        //
        //     return (
        //         <section>
        //             {products}
        //         </section>
        //     );
        // };
        // return currentProducts();
    }
});


controller.initApp();
