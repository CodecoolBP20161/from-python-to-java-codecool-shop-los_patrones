import com.codecool.shop.controller.CartController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.Cart;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        populateData();

        get("/session", (req, res) -> {
            return "Hello: " + req.session().attribute("count");
        });

        get("/session/:counter", (req, res) -> {
            req.session().attribute("count", req.params(":counter"));
            return "Hello: " + req.session().attribute("count");
        });

        get("/pay", ProductController::renderPay, new ThymeleafTemplateEngine());

        get("/createOrder", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                CartController.createOrder(gson.fromJson(request.body(), HashMap.class));
                response.redirect("/pay");
                return "";
            }
        });


        post("/tocart", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                ProductController.toCart(request, gson.fromJson(request.body(), HashMap.class));
                return ProductController.cart(request, response);
            }
        });

        get("/cart", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return ProductController.cart(request, response);
            }
        });

        post("/fromcart", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                ProductController.fromCart(request, gson.fromJson(request.body(), HashMap.class));
                return ProductController.cart(request, response);
            }
        });

        get("/categories", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return ProductController.getCategories(request, response);
            }
        });
        get("/suppliers", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return ProductController.getSuppliers(request, response);
            }
        });
        get("/products", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return ProductController.getProducts(request, response);
            }
        });

        get("/", ProductController::renderIndex, new ThymeleafTemplateEngine());


    }


    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier stuff = new Supplier("Stuff", "Stuff");
        supplierDataStore.add(stuff);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(laptop);
        ProductCategory amazing = new ProductCategory("Amazing", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(amazing);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

        productDataStore.add(new Product("Amazon Fire 222", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Whatever", 10000, "USD", "Whatever", laptop, amazon));
        productDataStore.add(new Product("Beni", 1.99f, "USD", "such Beni, much Vörös", amazing, stuff));

    }


}
