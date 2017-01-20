import React from 'react'

import LineItem from './cartmodal_components/lineitem'
import mediator from '../../../core/store'

class CartModal extends React.Component{
  constructor(){
    super()
  }
  closeModal(){
    document.getElementById('cartModal').classList.add('disappear')
    document.getElementById('cartModalBackground').classList.add('fadeaway')
    setTimeout(() => {
      mediator.store.dispatch({type: "CART_MODAL_CLOSED"})
    }, 1000)
  }
  render(){
    let products = this.props.data.products
    let totalPrice = this.props.data.totalPrice
    let totalQuantity = this.props.data.totalQuantity
    return (
      <div>
        <div className="webshop-modal" onWheel={(event) => {event.preventDefault()}} id="cartModal">
          <div className="modal-products" onWheel={(event) => {event.preventDefault()}}>
            {products.map((product) => (
              <LineItem key={product.id} lineitem={product}/>
            ))}
          </div>
          <div>
            <div className="modal-result">
              <div className="modal-number">{totalQuantity} products</div>
              <div className="modal-number">{totalPrice}$</div>
              <div className="modal-number"><Proceed/></div>
            </div>
            <Close action={this.closeModal}/>
          </div>
        </div>
        <div className="webshop-modal-background" id="cartModalBackground"></div>
      </div>
    )
  }
}

const Proceed = (props) => (
  <button>Proceed</button>
)

const Close = (props) => (
  <div>
    <button onClick={props.action.bind(this)} className="modal-back-button">
      Back
    </button>
  </div>
)

export default CartModal
