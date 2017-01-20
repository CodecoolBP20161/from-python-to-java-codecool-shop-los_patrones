import React from 'react'

import Option from './option'

class DropdownTab extends React.Component{
  constructor(){
    super()
  }
  render(){
    let options = this.props.options
    let chosen = this.props.chosen
    let action = this.props.action
    let params = this.props.params
    return (
      <div className="navbar-option-group">
        {options.length == 0 ? <Loading/> : <Dropdown options={options} chosen={chosen} action={action} params={params}/>}
      </div>
    )
  }
}

const Loading = (props) => (
  <div>
    Loading...
  </div>
)

const Dropdown = (props) => (
  <div className="option-group">
    {
      props.options.map((option) => (
        <Option key={option.name} option={option} action={props.action} chosen={props.chosen} params={props.params}/>
      )
    )}
  </div>
)

export default DropdownTab
