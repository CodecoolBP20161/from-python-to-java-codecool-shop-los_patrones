const initialState = {

  modalView: null,

  examined: null,

  fetchStatus: "fetching",

  searchParams: {
    category: "all",
    supplier: "all"
  },

  data: {
    products: [

    ],
    cart: {
      totalQuantity: 0,
      totalPrice: 0,
      products: []
    },
    recommendations: "none",
    categories: [

    ],
    suppliers: [

    ]
  }
}

export default initialState
