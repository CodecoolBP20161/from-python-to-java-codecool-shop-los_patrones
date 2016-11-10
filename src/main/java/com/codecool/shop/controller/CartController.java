package com.codecool.shop.controller;


import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.services.SessionLogger;
import spark.Request;

import java.util.HashMap;

public class CartController {
    public static SessionLogger logger = new SessionLogger("log.txt");

    public static void createOrder(Request request, HashMap data) {
        System.out.println(1);
        Cart cart = ProductController.setCart(request);
        System.out.println(2);
        System.out.println(data.get("id").toString());
        System.out.println(Float.parseFloat(data.get("id").toString()));
        System.out.println((int) Float.parseFloat(data.get("id").toString()));
        Order order = new Order(
            (int) Float.parseFloat(data.get("id").toString()),
            cart,
            data.get("firstName").toString(),
            data.get("lastName").toString(),
            data.get("email").toString(),
            data.get("phoneNumber").toString(),
            data.get("billingAddress").toString() + data.get("billingCountry").toString() +
                data.get("billingCity").toString() + data.get("billingZip").toString(),
            data.get("shippingAddress").toString() + data.get("shippingCountry").toString() +
                    data.get("shippingCity").toString() + data.get("shippingZip").toString()
        );
        System.out.println(3);
        logger.logCheckoutEvent(request.session().id());
        System.out.println(4);
    }

}
