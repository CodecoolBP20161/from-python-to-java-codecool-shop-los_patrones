package com.codecool.shop.controller;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.DaoFactory;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.SessionLogger;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class CartApi extends Api {

    private static SessionLogger logger = new SessionLogger("log.txt");

    private static Cart setCart(Request request){
        if(request.session().attribute("cart") == null){
            Cart cart = new Cart();
            request.session().attribute("cart", cart);
            return cart;
        }else{
            return request.session().attribute("cart");
        }
    }

    public static void createOrder(Request request) {
        HashMap data = parse(request);
        Cart cart = setCart(request);
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
        logger.logCheckoutEvent(request.session().id());
    }

    public static String cart(Request req, Response res){

        Cart cart = setCart(req);
        return stringify(cart.toDict());
    }

    public static void updateCart(Request req) {
        HashMap data = parse(req);
        Cart cart = setCart(req);
        DaoFactory factory = new DaoFactory();
        ProductDao dao = factory.getProductDao();
        Product product = dao.find(Integer.parseInt(data.get("id").toString()));
        LineItem item = new LineItem(product);
        if (data.get("method").toString().equals("add")) {
            cart.add(item);
        }
        if (data.get("method").toString().equals("remove")) {
            cart.remove(item);
        }
        logger.logPutIntoCartEvent(req.body() ,req.session().id());
    }

}
