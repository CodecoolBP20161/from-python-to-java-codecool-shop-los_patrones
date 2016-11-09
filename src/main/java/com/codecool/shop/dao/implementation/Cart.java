package com.codecool.shop.dao.implementation;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


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

    public ArrayList<LineItem> getItems() {
        return items;
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

    public String toJson(){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        for(LineItem item : this.items){
            names.add(item.getProduct().getName());
            prices.add(item.getProduct().getPrice());
            quantities.add(item.getQuantity());
        }

        HashMap<String, ArrayList> result = new HashMap<>();
        result.put("names", names);
        result.put("prices", prices);
        result.put("quantites", quantities);

        Gson gson = new Gson();

        return gson.toJson(result);
    }
}

