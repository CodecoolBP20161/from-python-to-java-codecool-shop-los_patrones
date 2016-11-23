package com.codecool.shop.model;

import java.util.HashMap;

public class LineItem {
    private Product product;
    private int quantity;
    private static int counter = 0;
    private int id;

    public LineItem(Product product){
        this.product = product;
        this.id = ++counter;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void incrementQuantity(){
        this.quantity++;
    }

    public void decrementQuantity(){
        this.quantity--;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString(){
        return "Product name: " + this.product.getName() + ", quantity: " + this.quantity;
    }

    public HashMap toDict() {
        HashMap returnDict = new HashMap<>();
        returnDict.put("id", product.getId());
        returnDict.put("name", product.getName());
        returnDict.put("price", product.getPrice());
        returnDict.put("currency", product.getDefaultCurrency().toString());
        returnDict.put("category", product.getProductCategory().getName());
        returnDict.put("supplier", product.getSupplier().getName());
        returnDict.put("description", product.getDescription());
        returnDict.put("quantity", getQuantity());
        return returnDict;
    }
}
