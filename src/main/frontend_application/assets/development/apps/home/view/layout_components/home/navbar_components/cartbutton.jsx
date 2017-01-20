import React from 'react'

import mediator from '../../../../core/store'
import datamanager from '../../../../../../common_components/js/datamanager'

class CartButton extends React.Component{
  constructor(){
    super()
  }
  openModal(){
    mediator.store.dispatch({type: "CART_MODAL_OPENED"})
  }
  render(){
    let quantity = this.props.quantity
    return (
      <div className="navbar-cart-place">
        <button onClick={this.openModal.bind(this)} className="navbar-cart-button">
          {quantity}
        </button>
      </div>
    )
  }
}

export default CartButton
