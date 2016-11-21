package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.LineItem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.*;
import com.codecool.shop.services.CartService;
import com.codecool.shop.services.JsonTransformer;
import com.codecool.shop.services.SessionLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ProductController {
    public static SessionLogger logger = new SessionLogger("log.txt");


    public static ModelAndView renderIndex(Request req, Response res) {
        HashMap params = new HashMap();
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderPay(Request req, Response res) {
        HashMap params = new HashMap();
        return new ModelAndView(params, "product/pay");
    }

    public static String cart(Request req, Response res){

        CartService cartService = new CartService();
        Cart cart = cartService.setCart(req);
        return cartService.cartToJson(cart, req);

    }

    public static void updateCart(Request req, HashMap json){

        CartService cartService = new CartService();
        Cart cart = cartService.setCart(req);
        ProductDao dao = ProductDaoMem.getInstance();
        Product product = dao.find(Integer.parseInt(json.get("id").toString()));
        LineItem item = new LineItem(product);
        if (json.get("method").toString().equals("add")) {
            cart.add(item);
        }
        if (json.get("method").toString().equals("remove")) {
            cart.remove(item);
        }
        logger.logPutIntoCartEvent(req.body() ,req.session().id());
    }

    public static String getCategories (Request req, Response res) {
        JsonTransformer transformer = new JsonTransformer();
        ArrayList categoryParams = ProductCategoryDaoMem.getDATA().stream().map(BaseModel::getName).collect(Collectors.toCollection(ArrayList::new));
        return transformer.render(categoryParams);
    }

    public static String getSuppliers (Request req, Response res) {
        JsonTransformer transformer = new JsonTransformer();
        ArrayList supplierParams = SupplierDaoMem.getDATA().stream().map(BaseModel::getName).collect(Collectors.toCollection(ArrayList::new));
        return transformer.render(supplierParams);
    }

    public static String getProducts (Request req, Response res) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map allParams = new HashMap();
        if (req.queryParams("category") != null) {
            allParams.put("category", req.queryParams("category"));
        }
        if (req.queryParams("supplier") != null) {
            allParams.put("supplier", req.queryParams("supplier"));
        }

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Map responseData = new HashMap<String, ArrayList>();
        ArrayList productList = new ArrayList<String>();

        ArrayList possibleCategories = new ArrayList<ProductCategory>();
        if (allParams.containsKey("category")) {
            String index = (String) allParams.get("category");
            possibleCategories.add(productCategoryDataStore.find((Integer.parseInt(index))));
        }
        else {
            for (int i=0; i<ProductCategoryDaoMem.getDATA().size(); i++) {
                possibleCategories.add(ProductCategoryDaoMem.getDATA().get(i));
            }
        }

        ArrayList possibleSuppliers = new ArrayList<Supplier>();
        if (allParams.containsKey("supplier")) {
            String index = (String) allParams.get("supplier");
            possibleSuppliers.add(supplierDataStore.find((Integer.parseInt(index))));
        }
        else {
            for (int i=0; i<SupplierDaoMem.getDATA().size(); i++) {
                possibleSuppliers.add(SupplierDaoMem.getDATA().get(i));
            }
        }

        for (int i=0; i<productDataStore.getAll().size(); i++) {
            if (possibleSuppliers.contains(productDataStore.getAll().get(i).getSupplier()) && possibleCategories.contains(productDataStore.getAll().get(i).getProductCategory())) {
                productList.add(productDataStore.getAll().get(i).toDict());
            }
        }

        responseData.put("Products", productList);

        return gson.toJson(responseData);
    }
}
