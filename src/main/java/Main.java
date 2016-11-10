import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

        ArrayList<HashMap> tasks = new ArrayList<>();

        HashMap<String, String> task = new HashMap<>();
        task.put("id", "1");
        task.put("title", "Buy groceries");
        task.put("description", "Milk, Cheese, Pizza, Fruit, Tylenol");
        task.put("done", "false");
        tasks.add(task);

        task = new HashMap<>();
        task.put("id", "2");
        task.put("title", "Buy groceries");
        task.put("description", "Milk, Cheese, Pizza, Fruit, Tylenol");
        task.put("done", "false");
        tasks.add(task);

        populateData();

        Cart cart = Cart.getInstance();


//        megfelelő a formátum, make public tasket implementálni kell?
        get("/todo/api/v1.0/tasks", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return tasks.toString();
            }
        });

        get("todo/api/v1.0/tasks/:id", new Route(){
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return null;
            }
        });
















//        populateData();


//        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

//        Cart cart = Cart.getInstance();

//        get("/tocart/:id", new Route(){
//            @Override
//            public String handle(Request request, Response response) throws Exception {
//                return ProductController.cartToJson(request, response);
//            }
//        });
//
//
//        get("/tocart", new Route(){
//            @Override
//            public String handle(Request request, Response response) throws Exception {
//                return ProductController.cartToJson(request, response);
//            }
//        });
//
//        get("/tocart/:id/:symbol", new Route(){
//            @Override
//            public String handle(Request request, Response response) throws Exception {
//                return ProductController.cartToJson(request, response);
//            }
//        });
//
////        post("/-quantity/:id", (request, response) -> {
////            cart.decreaseQuantity((int) Integer.parseInt(request.params(":id")));
////            return ProductController.cartToJson(request, response);
////        });
//
//        get("/initMain", new Route(){
//            @Override
//            public String handle(Request request, Response response) throws Exception {
//                return ProductController.indexMainResponse(request, response);
//            }
//        });
//        get("/indexSearch", new Route(){
//            @Override
//            public String handle(Request request, Response response) throws Exception {
//                return ProductController.indexSearch(request, response);
//            }
//        });
//        get("/example", new Route(){
//            @Override
//            public String handle(Request request, Response response) throws Exception {
//                return ProductController.buildJSON(request, response);
//            }
//        });
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
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(laptop);
        ProductCategory PC = new ProductCategory("PC", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(PC);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet2, amazon));
        productDataStore.add(new Product("Whatever", 10000, "HUF", "Whatever", laptop, amazon));
        productDataStore.add(new Product("Somethingelse", 10000, "HUF", "Something", PC, lenovo));

    }


}
