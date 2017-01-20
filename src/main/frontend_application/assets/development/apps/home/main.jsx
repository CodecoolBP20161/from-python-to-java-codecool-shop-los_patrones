import React from 'react'
import ReactDOM from 'react-dom'

require('../../common_components/css/style.css')
require('./view/style/style.css')

import Layout from './view/layout'
import mediator from './core/store'
import Initializer from './core/appinit'

const app = document.getElementById('app')

const render = () => {
  let state = mediator.store.getState()
  let modalView = state.modalView
  let data = state.data
  let fetchStatus = state.fetchStatus
  let searchParams = state.searchParams
  let examined = state.examined
  ReactDOM.render(
    <Layout modalView={modalView} fetchStatus={fetchStatus} data={data} searchParams={searchParams} examined={examined}/>,
    document.body
  )
}

mediator.store.subscribe(render)

Initializer.init()
render()
