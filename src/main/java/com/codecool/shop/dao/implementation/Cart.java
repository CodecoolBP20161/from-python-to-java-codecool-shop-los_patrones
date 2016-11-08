package com.codecool.shop.dao.implementation;

import java.util.ArrayList;


public class Cart {
    private ArrayList<LineItem> items = new ArrayList<>();
    private String status;
    private int id;
    private static int counter = 0;
    private int totalQantity;
    private float totalPrice;

    public Cart(){
        this.id = ++counter;
        this.status = "New";
        this.totalQantity = 0;
        this.totalPrice = 0;
    }

    public void add(LineItem item){
        for(LineItem currentItem : this.items){
            if(item.getProduct().getName() == currentItem.getProduct().getName()){
                currentItem.incrementQuantity();
                return;
            }
        }
        this.items.add(item);
    }

    public void remove(LineItem item){
        for(LineItem currentItem : this.items){
            if(item.getProduct().getName() == currentItem.getProduct().getName()){
                currentItem.decrementQuantity();
                return;
            }
        }
    }

    public void process(){
        this.totalQantity = 0;
        this.totalPrice = 0;
        for(LineItem item : this.items){
            if(item.getQuantity() == 0){
                this.items.remove(item);
            }else{
                this.totalQantity += item.getQuantity();
                this.totalPrice += item.getQuantity() * item.getProduct().getDefaultPrice();
            }
        }
    }

    public int getTotalItemNumber(){
        return this.totalQantity;
    }

    public float getTotalPrice(){
        return this.totalQantity;
    }

    public void checkOut(){
        this.status = "Checked out";
    }

    public String toString(){
        return "Id: " + this.id + ", status: " + this.status + ", items: " + this.items;
    }
}

