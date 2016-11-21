package com.codecool.shop.services;

import com.codecool.shop.dao.implementation.LineItem;
import com.codecool.shop.model.Cart;
import com.google.gson.Gson;
import spark.Request;

import java.util.ArrayList;
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


    public String cartToJsosss(Cart cart, Request request){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<Float> totalprice = new ArrayList<>();
        ArrayList<Integer> totalquantity = new ArrayList<>();
        ArrayList<Integer> id = new ArrayList<>();

        for(LineItem Item : cart.getItems()){
            names.add(Item.getProduct().getName());
            prices.add(Item.getProduct().getPrice());
            quantities.add(Item.getQuantity());
            id.add(Item.getProduct().getId());
        }

        totalprice.add(cart.getTotalPrice());
        totalquantity.add(cart.getTotalItemNumber());


        HashMap<String, ArrayList> result = new HashMap<>();
        result.put("names", names);
        result.put("prices", prices);
        result.put("quantites", quantities);
        result.put("totalprice", totalprice);
        result.put("totalquantity", totalquantity);
        result.put("id", id);

        Gson gson = new Gson();

        return gson.toJson(result);
    }
}
