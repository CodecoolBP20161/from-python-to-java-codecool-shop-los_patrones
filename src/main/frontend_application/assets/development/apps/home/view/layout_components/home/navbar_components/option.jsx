import React from 'react'

class Option extends React.Component{
  constructor(){
    super()
    this.active = false
  }
  render(){
    let opt = this.props.option
    let chosen = this.props.chosen
    let action = this.props.action
    let params = this.props.params
    this.active = chosen == opt.id
    return (
      <button value={opt.id} onClick={() => action(opt.id, params)} className="navbar-option" style={this.active ? {backgroundColor: "#337ab7"} : {backgroundColor: "#5bc0de"}}>
        {opt.name}
      </button>
    )
  }
}

export default Option
