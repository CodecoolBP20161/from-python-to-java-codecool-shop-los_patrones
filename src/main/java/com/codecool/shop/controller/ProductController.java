package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.services.CartService;
import com.codecool.shop.services.JsonTransformer;
import com.codecool.shop.services.SessionLogger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class ProductController {
    private static SessionLogger logger = new SessionLogger("log.txt");

    public static ModelAndView renderIndex(Request req, Response res) {
        HashMap params = new HashMap();
        return new ModelAndView(params, "product/index");
    }

    public static ModelAndView renderPay(Request req, Response res) {
        HashMap params = new HashMap();
        return new ModelAndView(params, "product/pay");
    }


    public static String cart(Request req){

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
        JsonTransformer transformer = new JsonTransformer();
        ProductDao pDao = ProductDaoMem.getInstance();
        ProductCategoryDao cDao = ProductCategoryDaoMem.getInstance();
        SupplierDao sDao = SupplierDaoMem.getInstance();
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
        return transformer.render(returnData);
    }
}
