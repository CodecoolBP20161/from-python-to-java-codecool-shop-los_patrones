package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.google.gson.*;

import com.google.gson.internal.Streams;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
