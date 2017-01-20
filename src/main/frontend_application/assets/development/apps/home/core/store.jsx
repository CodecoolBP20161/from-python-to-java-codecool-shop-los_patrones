import { createStore } from 'redux'
import initialState from './state_tree'

const mediator = (() => {

  const initState = initialState

  const reducer = (state = initState, action) => {
    let nextState = state
    let data
    let to
    let product
    let productId
    let productName
    let price
    switch (action.type) {

      case "PRODUCTS_ARRIVED":
        data = action.data
        if (data.category == nextState.searchParams.category && data.supplier == nextState.searchParams.supplier) {
          nextState.data.products = data.products
          nextState.fetchStatus = "done"
        }
        return nextState

      case "CATEGORIES_ARRIVED":
        data = action.data
        nextState.data.categories = data
        nextState.data.categories.push({id: "all", name: "All categories"})
        return nextState

      case "SUPPLIERS_ARRIVED":
        data = action.data
        nextState.data.suppliers = data
        nextState.data.suppliers.push({id: "all", name: "All suppliers"})
        return nextState

      case "CATEGORY_SET":
        to = action.to
        nextState.searchParams.category = to
        nextState.fetchStatus = "fetching"
        return nextState

      case "SUPPLIERS_SET":
        to = action.to
        nextState.searchParams.supplier = to
        nextState.fetchStatus = "fetching"
        return nextState

      case "INFO_MODAL_OPENED":
        product = action.product
        nextState.examined = product
        nextState.modalView = "infomodal"
        return nextState

      case "INFO_MODAL_CLOSED":
        nextState.modalView = null
        nextState.examined = null
        nextState.data.recommendations = "none"
        return nextState

      case "REVIEWS_ARRIVED":
        data = action.data
        if (data.product ==  nextState.examined){
          nextState.data.recommendations = data.recommendations
        }
        return nextState

      case "CART_MODAL_OPENED":
        nextState.modalView = "cartmodal"
        return nextState

      case "CART_MODAL_CLOSED":
        nextState.modalView = null
        return nextState

      case "PRODUCT_ADDED":
        product = action.product
        nextState.data.cart.totalPrice += product.price
        nextState.data.cart.totalQuantity += 1
        if (!nextState.data.cart.products.map(item => item.id).includes(product.id)) {
          let lineitem = product
          lineitem.quantity = 0
          nextState.data.cart.products.push(lineitem)
        }
        nextState.data.cart.products = nextState.data.cart.products.map((lineitem) => {
          if (lineitem.id == product.id) {
            lineitem.quantity += 1
          }
          return lineitem
        })
        return nextState

      case "PRODUCT_REMOVED":
        product = action.product
        nextState.data.cart.totalPrice -= product.price
        nextState.data.cart.totalQuantity -= 1
        nextState.data.cart.products = nextState.data.cart.products.map((lineitem) => {
          if (lineitem.id == product.id) {
            lineitem.quantity -= 1
          }
          return lineitem
        })
        let removeByAttr = (arr, attr, value) => {
            let i = arr.length;
            while(i--){
               if( arr[i] && arr[i].hasOwnProperty(attr) && (arguments.length > 2 && arr[i][attr] === value ) ){
                   arr.splice(i,1);
               }
            }
            return arr;
        }
        nextState.data.cart.products = removeByAttr(nextState.data.cart.products, "quantity", 0)
        return nextState

      case "CART_UPDATED":
        data = action.data
        nextState.data.cart = data
        return nextState

      default:
        return nextState
    }
  }

  const store = createStore(reducer)

  return {store}

})()

export default mediator
