import React from 'react'

import NavBar from './layout_components/home/navbar'
import Products from './layout_components/home/products'
import CartModal from './layout_components/modals/cartmodal'
import InfoModal from './layout_components/modals/infomodal'
import Contact from './layout_components/home/contact'


class Layout extends React.Component{
  constructor(){
    super()
  }
  render(){
    let modalView = this.props.modalView
    let examined = this.props.examined
    let fetchStatus = this.props.fetchStatus
    let searchParams = this.props.searchParams
    let data = this.props.data
    return (
      <div>
        <NavBar params={searchParams} cart={data.cart} suppliers={data.suppliers} categories={data.categories}/>
        <Products data={data.products} status={fetchStatus}/>
        {modalView == "cartmodal" ? <CartModal data={data.cart}/> : null}
        {modalView == "infomodal" ? <InfoModal data={data.recommendations} examined={examined}/> : null}
        <Contact/>
      </div>
    )
  }
}

export default Layout
