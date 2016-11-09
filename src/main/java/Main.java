import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        populateData();


        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
//        post("/tocart/:id", ProductController::getToCart, new ThymeleafTemplateEngine());
//        get("/hello", (req, res) -> "Hello World");
//        Gson gson = new Gson();
        Cart cart = Cart.getInstance();
//        Supplier amazon = new Supplier("Amazon", "Digital content and services");
//        get("/hello", (request, response) -> cart, gson::toJson);
//        get("/tocart/:id", (req, res) -> "Hello World");
        get("/tocart/:id", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return ProductController.cartToJson(request, response);
            }
        });


        get("/tocart", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return ProductController.cartToJson(request, response);
            }
        });

        post("/+quantity/:id", (request, response) -> {
            cart.increaseQuantity((int) Integer.parseInt(request.params(":id")));
            return ProductController.cartToJson(request, response);
        });

        post("/-quantity/:id", (request, response) -> {
            cart.decreaseQuantity((int) Integer.parseInt(request.params(":id")));
            return ProductController.cartToJson(request, response);
        });


    }


    public static void populateData() {


        Cart cart = Cart.getInstance();
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory tablet2 = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        productCategoryDataStore.add(tablet2);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet2, amazon));

    }


}
