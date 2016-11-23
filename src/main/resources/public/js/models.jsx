var SupplierBarComponent = React.createClass({

    render: function() {
        const suppliers = this.props.data.map(function(supplier){
            return (
                <option id={'supplier' + supplier.id} onClick={controller.doSomething} key={supplier.id}>{supplier.name}</option>
            );
        }, this);

        return(
            <div>
                <option>All suppliers</option>
                {suppliers}
            </div>
        );

    }
});

var CategoryBarComponent = React.createClass({
    render: function() {

        const categories = this.props.data.map(function(category){
            return (
                <option key={category.id}>{category.name}</option>
            );
        });

        return(
            <div>
                <option>All categories</option>
                {categories}
            </div>
        );

    }
});

var CartComponent = React.createClass({
    render: function() {
        const cartContent = this.props.data.map(function(cartRow){
            return (
                <tr key={cartRow.id}>
                    <td>{cartRow.name}</td>
                    <td>{cartRow.quantity}</td>
                    <td id="quant">{cartRow.price}</td>
                    <td><button type='button' className='button glyphicon glyphicon-minus' id={cartRow.id}></button></td>
                    <td><button type='button' className='button glyphicon glyphicon-plus' id={cartRow.id}></button></td>
                </tr>
            );
        });
        return(
            <tbody>{cartContent}</tbody>
        );
    }
});

var ProductComponent = React.createClass({
    render: function() {

        const currentProducts = this.props.data.map(function(product){
            console.log(product);
            return(
                <div key={product.id} className="item col-xs-4 col-sm-4 col-md-4 col-lg-4 thumbnail">
                    <img className="group list-group-image" src={"/img/product_" + product.id + ".jpg"}/>
                    <div className="caption">
                        <h4 className="group inner list-group-item-heading">{product.name}</h4>
                        <p className="group inner list-group-item-text">{product.description}</p>
                        <p>
                            <span>{product.price} {product.currency}</span>
                            <button id={product.id} className="btn btn-success">Add to cart</button>
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