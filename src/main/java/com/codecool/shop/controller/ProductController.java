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

    public static ModelAndView renderProducts(Request req, Response res) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        Map params = new HashMap<>();
        ArrayList pclist = new ArrayList<ProductCategory>();
        for (int i=0; i<ProductCategoryDaoMem.getDATA().size(); i++) {
            pclist.add(ProductCategoryDaoMem.getDATA().get(i));
        }
        ArrayList suplist = new ArrayList<Supplier>();
        for (int i=0; i<SupplierDaoMem.getDATA().size(); i++) {
            suplist.add(SupplierDaoMem.getDATA().get(i));
        }

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

    public static String cartToJson(Request req, Response res){
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        Cart cart = Cart.getInstance();
        System.out.println(cart.getItems());
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<Float> totalprice = new ArrayList<>();
        ArrayList<Integer> totalquantity = new ArrayList<>();

        if(req.params(":id") != null && req.params(":symbol") == null){
            int id = parseInt(req.params(":id"));
            Product product = productDataStore.find(id);
            LineItem item = new LineItem(product);
            System.out.println("asdasd");
            cart.add(item);
        }
        if(req.params(":symbol") != null){
            if (req.params(":symbol").equals("+"))
                System.out.println("+");
                System.out.println(cart.getItems());
                cart.increaseQuantity(parseInt(req.params(":id")));
            if (req.params(":symbol").equals("-"))
                System.out.println("-");
                cart.decreaseQuantity(parseInt(req.params(":id")));
        }


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

    public static void increaseQuantity(Request req, Response res){
        Cart cart = Cart.getInstance();
        cart.increaseQuantity((int) Integer.parseInt(req.params(":id")));
        cartToJson(req, res);
    }

    public static String buildJSON (Request req, Response res) {
        HashMap<String, String[]> map = new HashMap();
        String[] myList = {"lajos", "kazmer"};
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        map.put("bela", myList);
        return gson.toJson(map);
    }

    public static String indexMainResponse (Request req, Response res) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map responseData = new HashMap<>();
        ArrayList categoryParams = new ArrayList<ProductCategory>();
        for (int i=0; i<ProductCategoryDaoMem.getDATA().size(); i++) {
            Map oneCategory = new HashMap<>();
            oneCategory.put("name", ProductCategoryDaoMem.getDATA().get(i).getName());
            oneCategory.put("id", ProductCategoryDaoMem.getDATA().get(i).getId());
            categoryParams.add(oneCategory);
        }
        ArrayList suplierParams = new ArrayList<Supplier>();
        for (int i=0; i<SupplierDaoMem.getDATA().size(); i++) {
            Map oneSupplier = new HashMap<>();
            oneSupplier.put("name", SupplierDaoMem.getDATA().get(i).getName());
            oneSupplier.put("id", SupplierDaoMem.getDATA().get(i).getId());
            suplierParams.add(oneSupplier);
        }
        responseData.put("Category", categoryParams);
        responseData.put("Supplier", suplierParams);
        return gson.toJson(responseData);
    }

    public static String indexSearch (Request req, Response res) {
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
