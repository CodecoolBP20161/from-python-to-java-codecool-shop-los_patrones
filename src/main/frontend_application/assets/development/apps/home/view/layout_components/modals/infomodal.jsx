import React from 'react'

import LinkButton from './infomodal_components/linkbutton'
import mediator from '../../../core/store'

class InfoModal extends React.Component{
  constructor(){
    super()
  }
  closeModal(){
    document.getElementById('infoModal').classList.add('disappear')
    document.getElementById('infoModalBackground').classList.add('fadeaway')
    setTimeout(() => {
      mediator.store.dispatch({type: "INFO_MODAL_CLOSED"})
    }, 1000)
  }
  render(){
    let recommendations = this.props.data
    let productName = this.props.examined
    return(
      <div>
        <div className="webshop-modal" onWheel={(event) => {event.preventDefault()}} id="infoModal">
          <div className="modal-content">
            <h4>Reviews for {productName}</h4>
            {recommendations == "none" ? <LoadingRecommendations/> : recommendations.map((recommendation) => (
              <LinkButton key={recommendation} text={recommendation}/>
            ))}
          </div>
          <Close action={this.closeModal}/>
        </div>
        <div className="webshop-modal-background" onWheel={(event) => {event.preventDefault()}} id="infoModalBackground"></div>
      </div>
    )
  }
}

const LoadingRecommendations = () => (
  <div>
    <h1 className="modal-loading">Loading...</h1>
  </div>
)

const Close = (props) => (
  <div>
    <button onClick={props.action} className="modal-back-button">
      Back
    </button>
  </div>
)

export default InfoModal
