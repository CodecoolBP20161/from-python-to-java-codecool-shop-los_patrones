import React from 'react'

import datamanager from '../../../../../common_components/js/datamanager'
import mediator from '../../../core/store'

import DropdownTab from './navbar_components/dropdowntab'
import CartButton from './navbar_components/cartbutton'

class NavBar extends React.Component{
  constructor(){
    super()
  }
  setCategory(chosen, params){
    let supplier = params.supplier
    let request = {
      method: "GET",
      destination: "products?category=" + chosen + "&supplier=" + supplier,
      action: "PRODUCTS_ARRIVED"
    }
    datamanager.JSONtransfer(request, mediator)
    mediator.store.dispatch({type: "CATEGORY_SET", to: chosen})
  }
  setSupplier(chosen, params){
    let category = params.category
    let request = {
      method: "GET",
      destination: "products?supplier=" + chosen + "&category=" + category,
      action: "PRODUCTS_ARRIVED"
    }
    datamanager.JSONtransfer(request, mediator)
    mediator.store.dispatch({type: "SUPPLIERS_SET", to: chosen})
  }
  render(){
    let params = this.props.params
    let cart = this.props.cart
    let categories = this.props.categories
    let suppliers = this.props.suppliers
    return (
      <div className="shop-navbar">
        <div className="navbar-row">
          <Logo/>
          <CartButton quantity={cart.totalQuantity}/>
        </div>
        <div className="navbar-row">
          <DropdownTab options={categories} chosen={params.category} params={params} action={this.setCategory}/>
          <DropdownTab options={suppliers} chosen={params.supplier} params={params} action={this.setSupplier}/>
        </div>
      </div>
    )
  }
}

const Logo = (props) => (
  <div className="navbar-logo">
    <h1>Codecool Shop</h1>
  </div>
)

export default NavBar
