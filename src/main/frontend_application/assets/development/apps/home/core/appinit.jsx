import datamanager from '../../../common_components/js/datamanager'
import mediator from './store'

const Initializer = (() => {

  let getCategories = () => {
    let request = {
      method: "GET",
      destination: "categories",
      action: "CATEGORIES_ARRIVED"
    }
    datamanager.JSONtransfer(request, mediator)
  }

  let getSuppliers = () => {
    let request = {
      method: "GET",
      destination: "suppliers",
      action: "SUPPLIERS_ARRIVED"
    }
    datamanager.JSONtransfer(request, mediator)
  }

  let getCart = () => {
    let request = {
      method: "GET",
      destination: "cart",
      action: "CART_UPDATED"
    }
    datamanager.JSONtransfer(request, mediator)
  }

  let getProducts = () => {
    let request = {
      method: "GET",
      destination: "products?category=all&supplier=all",
      action: "PRODUCTS_ARRIVED"
    }
    datamanager.JSONtransfer(request, mediator)
  }

  let init = () => {
    getCategories()
    getSuppliers()
    getCart()
    getProducts()
  }

  const API = {init}

  return API

})()

export default Initializer
