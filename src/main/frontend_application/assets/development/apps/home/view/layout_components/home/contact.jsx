import React from 'react'

class Contact extends React.Component{
  constructor(){
    super()
  }
  render(){
    return (
      <div style={{textAlign: "center"}}>
        <h1>Contact US</h1>
        <form action="http://localhost:4567/toth910719balint@gmail.com" method="POST">
        <input type="text" name="name" placeholder="name" style={{padding: "10px"}}/>
        <input type="email" name="_replyto" placeholder="email" style={{padding: "10px"}}/>
        <input type="text" name="message" placeholder="message" style={{padding: "10px"}}/>
        <input type="submit" value="Send" style={{padding: "10px", backgroundColor: "blue", border: "1px solid blue", color: "white"}}/>
      </form>
      </div>
    )
  }
}

export default Contact
