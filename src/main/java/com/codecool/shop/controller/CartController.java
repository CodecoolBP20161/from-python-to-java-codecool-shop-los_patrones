package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import spark.Request;

import java.util.HashMap;

public class CartController {
    public static void createOrder(Request request, HashMap data) {
        Cart cart = ProductController.setCart(request);

        Order order = new Order(
                Integer.parseInt(data.get("id").toString()),
                cart,
                data.get("firstName").toString(),
                data.get("lastName").toString(),
                data.get("email").toString(),
                data.get("phoneNumber").toString(),
                data.get("billingAddress").toString(),
                data.get("shippingAddress").toString()
        );
    }

}
