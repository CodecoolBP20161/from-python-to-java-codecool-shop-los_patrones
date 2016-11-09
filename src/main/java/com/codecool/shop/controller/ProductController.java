package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.dao.implementation.LineItem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class ProductController {

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        Map params = new HashMap<>();
        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        return new ModelAndView(params, "product/index");


    }

    public static ModelAndView getToCart(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        Cart cart = Cart.getInstance();

        int id = parseInt(req.params(":id"));
        Product product = productDataStore.find(id);
        LineItem item = new LineItem(product);
        cart.add(item);

        Map params = new HashMap<>();

        System.out.println(productDataStore.find(id));
        params.put("category", productCategoryDataStore.find(1));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        params.put("cart", cart.getTotalItemNumber());
        System.out.println(cart.toString());
        return new ModelAndView(params, "product/index");


    }

    public static String CarttoJson(Request req, Response res){
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        Cart cart = Cart.getInstance();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<Float> totalprice = new ArrayList<>();
        ArrayList<Integer> totalquantity = new ArrayList<>();
        int id = parseInt(req.params(":id"));
        Product product = productDataStore.find(id);
        LineItem item = new LineItem(product);
        cart.add(item);

        for(LineItem Item : cart.getItems()){
            names.add(Item.getProduct().getName());
            prices.add(Item.getProduct().getPrice());
            quantities.add(Item.getQuantity());
        }
        totalprice.add(cart.getTotalPrice());
        System.out.println(cart.getTotalPrice());
        totalquantity.add(cart.getTotalItemNumber());


        HashMap<String, ArrayList> result = new HashMap<>();
        result.put("names", names);
        result.put("prices", prices);
        result.put("quantites", quantities);
        result.put("totalprice", totalprice);
        result.put("totalquantity", totalquantity);

        Gson gson = new Gson();

        return gson.toJson(result);
    }

}
