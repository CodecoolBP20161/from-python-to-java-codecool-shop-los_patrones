import com.codecool.shop.controller.CartApi;
import com.codecool.shop.controller.SearchApi;
import com.codecool.shop.controller.TemplateController;
import com.codecool.shop.controller.UserController;
import com.codecool.shop.service.AppInit;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;


public class Main {

    public static void main(String[] args) {

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        AppInit initializer = new AppInit();
        initializer.initApp("");

        post("/createOrder", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                CartApi.createOrder(request);
                return "";
            }
        });

        post("/updatecart", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                CartApi.updateCart(request);
                return CartApi.cart(request, response);
            }
        });

        get("/cart", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return CartApi.cart(request, response);
            }
        });

        get("/categories", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return SearchApi.getCategories(request, response);
            }
        });

        get("/suppliers", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return SearchApi.getSuppliers(request, response);
            }
        });

        get("/products", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                return SearchApi.getProducts(request, response);
            }
        });

        get("/signup", new Route(){
            @Override
            public String handle(Request request, Response response) throws Exception {
                UserController controller = new UserController(request, response);
                return controller.manageRegProcess();
            }
        });

        get("/pay", TemplateController::renderPay, new ThymeleafTemplateEngine());

        get("/", TemplateController::renderIndex, new ThymeleafTemplateEngine());

    }
}
