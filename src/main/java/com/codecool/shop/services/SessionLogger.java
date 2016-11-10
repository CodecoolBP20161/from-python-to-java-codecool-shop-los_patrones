package com.codecool.shop.services;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SessionLogger {
    String path;

    public SessionLogger(String path){
        this.path = path;
        this.writeToFile("");
    }

    public void writeToFile(String message){
        try{
            FileWriter writer = new FileWriter(this.path, true);
            BufferedWriter bw = new BufferedWriter(writer);
            PrintWriter out = new PrintWriter(bw);
            out.println(message);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logPutIntoCartEvent(String product, String id) {
        String message = "Put product into the cart from id: " + id + " " + product.toString();
        this.writeToFile(message);
    }

    public void logDeleteFromCartEvent(String product, String id) {
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
