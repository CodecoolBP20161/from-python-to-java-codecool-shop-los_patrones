import React from 'react'

import mediator from '../../../../core/store'
import datamanager from '../../../../../../common_components/js/datamanager'

class LineItem extends React.Component{
  constructor(){
    super()
  }
  addToCart(product){
    let data = {
      id: product.id.toString(),
      method: "add"
    }
    let request = {
      method: "POST",
      destination: "updateCart",
      action: "None",
      data: data
    }
    // datamanager.JSONtransfer(request, mediator)
    mediator.store.dispatch({type: "PRODUCT_ADDED", product: product})
  }
  removeFromCart(product){
    let data = {
      id: product.id.toString(),
      method: "remove"
    }
    let request = {
      method: "POST",
      destination: "updateCart",
      action: "None",
      data: data
    }
    // datamanager.JSONtransfer(request, mediator)
    mediator.store.dispatch({type: "PRODUCT_REMOVED", product: product})
  }
  render(){
    let product = this.props.lineitem
    return (
      <div className="lineitem-container">
        <div className="lineitem-info">
          <span>{product.name}</span>
        </div>
        <div className="lineitem-price">
          <span>{product.price}$</span>
        </div>
        <div className="lineitem-controller">
          <div className="cart-modifier-container"><CartButton sign={"-"} action={() => this.removeFromCart(product)}/></div>
          <div className="subtotal">{product.quantity}</div>
          <div className="cart-modifier-container"><CartButton sign={"+"} action={() => this.addToCart(product)}/></div>
          <div className="subtotal price">{product.quantity * product.price}$</div>
        </div>
      </div>
    )
  }
}

const CartButton = (props) => (
  <button onClick={props.action.bind(this)} className="cart-modifier">{props.sign}</button>
)

export default LineItem
