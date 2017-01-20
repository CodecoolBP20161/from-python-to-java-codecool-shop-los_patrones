import React from 'react'

import mediator from '../../../../core/store'
import datamanager from '../../../../../../common_components/js/datamanager'

class AddButton extends React.Component{
  constructor(props){
    super(props)
    this.product = props.product
  }
  addToCart(event){
    let data = {
      id: this.product.id.toString(),
      method: "add"
    }
    let request = {
      method: "POST",
      destination: "updatecart",
      action: "None",
      data: data
    }
    // datamanager.JSONtransfer(request, mediator)
    mediator.store.dispatch({type: "PRODUCT_ADDED", product: this.product})
  }
  render(){
    return(
      <div className="product-button-place">
        <button onClick={this.addToCart.bind(this)} className="product-button">
          Add to cart
        </button>
      </div>
    )
  }
}

export default AddButton
