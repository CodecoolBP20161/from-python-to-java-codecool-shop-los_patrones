package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;


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
}
