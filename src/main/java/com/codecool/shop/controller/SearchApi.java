package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DaoFactory;
import com.codecool.shop.model.*;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class SearchApi extends Api{

    public static String getCategories (Request req, Response res) {
        ArrayList<HashMap<String, java.io.Serializable>> categoryParams = new ArrayList<>();
        DaoFactory factory = new DaoFactory();
        ProductCategoryDao dao = factory.getCategoryDao();
        for (ProductCategory category : dao.getAll()) {
            HashMap<String, java.io.Serializable> categoryData = new HashMap<>();
            categoryData.put("id", category.getId());
            categoryData.put("name", category.getName());
            categoryParams.add(categoryData);
        }
        return stringify(categoryParams);
    }

    public static String getSuppliers (Request req, Response res) {
        ArrayList<HashMap<String, java.io.Serializable>> supplierParams = new ArrayList<>();
        DaoFactory factory = new DaoFactory();
        SupplierDao dao = factory.getSupplierDao();
        for (Supplier supplier : dao.getAll()) {
            HashMap<String, java.io.Serializable> supplierData = new HashMap<>();
            supplierData.put("id", supplier.getId());
            supplierData.put("name", supplier.getName());
            supplierParams.add(supplierData);
        }
        return stringify(supplierParams);
    }

    public static String getProducts (Request req, Response res) {
        DaoFactory factory = new DaoFactory();
        ProductDao pDao = factory.getProductDao();
        ProductCategoryDao cDao = factory.getCategoryDao();
        SupplierDao sDao = factory.getSupplierDao();
        String category = req.queryParams("category");
        String supplier = req.queryParams("supplier");
        List<Product> allProducts = null;

        if (category != null && supplier != null) {
            allProducts = pDao.getBy(cDao.find(Integer.parseInt(category)), sDao.find(Integer.parseInt(supplier)));
        }
        if (category != null && supplier == null) {
            allProducts = pDao.getBy(cDao.find(Integer.parseInt(category)));
        }
        if (category == null && supplier != null) {
            allProducts = pDao.getBy(sDao.find(Integer.parseInt(supplier)));
        }
        if (category == null && supplier == null) {
            allProducts = pDao.getAll();
        }

        ArrayList returnData = allProducts.stream().map(Product::toDict).collect(Collectors.toCollection(ArrayList::new));
        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("category", category);
        returnMap.put("supplier", supplier);
        returnMap.put("products", returnData);
        return stringify(returnMap);
    }
}
