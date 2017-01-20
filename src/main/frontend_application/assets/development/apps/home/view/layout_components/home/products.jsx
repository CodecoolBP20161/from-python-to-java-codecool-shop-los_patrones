import React from 'react'

import InfoButton from './product_components/infobutton'
import AddButton from './product_components/addbutton'
import mediator from '../../../core/store'
import datamanager from '../../../../../common_components/js/datamanager'

class Products extends React.Component{
  constructor(){
    super()
  }
  render(){
    let products = this.props.data
    let status = this.props.status
    return (
      <div className="all-product-container">
        {status == "fetching" ? <Loading/> : products.map((product) => (
          <div key={product.id} className="product-container">
            <p>{product.name}</p>
            <p>{product.description}</p>
            <div className="product-buttons">
              <InfoButton productName={product.name}/>
              <AddButton product={product}/>
            </div>
          </div>
        ))}
      </div>
    )
  }
}

const Loading = (props) => (
  <div className="products-loading">
    Loading...
  </div>
)

export default Products
