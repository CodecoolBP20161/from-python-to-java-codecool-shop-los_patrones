package com.codecool.shop.services;

import com.codecool.shop.model.Cart;
import spark.Request;

import java.util.HashMap;

public class CartService {

    public Cart setCart(Request request){
        if(request.session().attribute("cart") == null){
            Cart cart = new Cart();
            request.session().attribute("cart", cart);
            return cart;
        }else{
            return request.session().attribute("cart");
        }
    }

    public String cartToJson(Cart cart, Request request){
        JsonTransformer jsonTransformer = new JsonTransformer();
        HashMap data = cart.toDict();
        return jsonTransformer.render(data);
    }

}
