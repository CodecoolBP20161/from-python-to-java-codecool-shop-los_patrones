package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.google.gson.Gson;
import spark.ModelAndView;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.*;
import spark.Request;
import spark.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.lang.Integer.parseInt;


public class ProductController {

    public static ModelAndView renderIndex(Request req, Response res) {
        HashMap params = new HashMap();
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderPay(Request req, Response res) {
        HashMap params = new HashMap();
        return new ModelAndView(params, "product/pay");
    }

    public static String cart(Request req, Response res){
        Cart cart = Cart.getInstance();
        System.out.println(cart.getItems());
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<Float> totalprice = new ArrayList<>();
        ArrayList<Integer> totalquantity = new ArrayList<>();


        for(LineItem Item : cart.getItems()){
            names.add(Item.getProduct().getName());
            prices.add(Item.getProduct().getPrice());
            quantities.add(Item.getQuantity());
        }

        totalprice.add(cart.getTotalPrice());
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

    public static void toCart(HashMap json){
        ProductDao productDataStore = ProductDaoMem.getInstance();
        Cart cart = Cart.getInstance();
        int id = 0;
        for (Object value : json.values()) {
            id = Integer.parseInt(value.toString());
        }
        Product product = productDataStore.find(id);
        LineItem item = new LineItem(product);
        cart.add(item);
        System.out.println(cart);
    }

    public static void fromCart(HashMap json){
        Cart cart = Cart.getInstance();
        int id = 0;
        for (Object value : json.values()) {
            id = Integer.parseInt(value.toString());
        }
        cart.decreaseQuantity(id);
    }

    public static String getCategories (Request req, Response res) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map responseData = new HashMap<>();
        ArrayList categoryParams = new ArrayList<ProductCategory>();
        for (int i=0; i<ProductCategoryDaoMem.getDATA().size(); i++) {
            Map oneCategory = new HashMap<>();
            oneCategory.put("name", ProductCategoryDaoMem.getDATA().get(i).getName());
            oneCategory.put("id", ProductCategoryDaoMem.getDATA().get(i).getId());
            categoryParams.add(oneCategory);
        }
        responseData.put("Category", categoryParams);
        return gson.toJson(responseData);
    }

    public static String getSuppliers (Request req, Response res) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map responseData = new HashMap<>();
        ArrayList supplierParams = new ArrayList<Supplier>();
        for (int i=0; i<SupplierDaoMem.getDATA().size(); i++) {
            Map oneSupplier = new HashMap<>();
            oneSupplier.put("name", SupplierDaoMem.getDATA().get(i).getName());
            oneSupplier.put("id", SupplierDaoMem.getDATA().get(i).getId());
            supplierParams.add(oneSupplier);
        }
        responseData.put("Supplier", supplierParams);
        return gson.toJson(responseData);
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
