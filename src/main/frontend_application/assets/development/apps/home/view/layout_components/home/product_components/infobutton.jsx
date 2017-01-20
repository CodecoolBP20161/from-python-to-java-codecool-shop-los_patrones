import React from 'react'
import datamanager from '../../../../../../common_components/js/datamanager'
import mediator from '../../../../core/store'

class InfoButton extends React.Component{
  constructor(){
    super()
    this.productName = null
  }
  getReviews(event){
    let request = {
      method: "GET",
      destination: "review?name=" + this.productName.split(" ").join(""),
      action: "REVIEWS_ARRIVED"
    }
    datamanager.JSONtransfer(request, mediator)
    mediator.store.dispatch({type: "INFO_MODAL_OPENED", product: this.productName.split(" ").join("")})
  }
  render(){
    let productName = this.props.productName
    this.productName = productName
    return (
      <div className="product-button-place">
        <button onClick={this.getReviews.bind(this)} className="product-button">
          Recommendations
        </button>
      </div>
    )
  }
}

export default InfoButton
