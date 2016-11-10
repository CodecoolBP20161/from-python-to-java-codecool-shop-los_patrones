package services;


import com.codecool.shop.model.Product;

import java.io.PrintWriter;

public class SessionLogger {
    String path;

    public SessionLogger(String path){
        this.path = path;
        this.writeToFile("");
    }

    public void writeToFile(String message){
        try{
            PrintWriter writer = new PrintWriter(this.path, "UTF-8");
            writer.println(message);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void logPutIntoCartEvent(String id, Product product) {
        String message = "Put product into the cart from id: " + id + " " + product.toString();
        this.writeToFile(message);
    }

    public void logDeleteFromCartEvent(String id, Product product) {
        String message = "Deleted product into the cart from id: " + id + " " + product.toString();
        this.writeToFile(message);
    }

    public void logCheckoutEvent(String id) {
        String message = "User checked out with id: " + id;
        this.writeToFile(message);
    }

    public void logPayEvent(String id) {
        String message = "User successfully paid with id: " + id;
        this.writeToFile(message);
    }
}
