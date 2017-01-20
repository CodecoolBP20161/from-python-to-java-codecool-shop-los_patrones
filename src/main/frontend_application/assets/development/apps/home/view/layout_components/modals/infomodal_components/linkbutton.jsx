import React from 'react'

class LinkButton extends React.Component{
  constructor(){
    super()
    this.text = null
  }
  openNewTab(event){
    window.open(this.text)
  }
  render(){
    let text = this.props.text
    this.text = text
    return (
        <div>
          <button onClick={this.openNewTab.bind(this)} className="link-button">
            {text}
          </button>
        </div>
    )
  }
}

export default LinkButton
