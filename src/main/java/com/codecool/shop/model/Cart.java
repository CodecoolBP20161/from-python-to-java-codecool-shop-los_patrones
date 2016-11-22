package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;


public class Cart{
    private ArrayList<LineItem> items = new ArrayList<>();
    private String status;
    private int id;
    private int totalQantity;
    private float totalPrice;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Cart(){
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
                this.process();
                return;
            }
        }
        this.items.add(item);
        this.process();
    }

    public void remove(LineItem item){
        for(LineItem currentItem : this.items){
            if(Objects.equals(item.getProduct().getName(), currentItem.getProduct().getName())){
                currentItem.decrementQuantity();
                this.process();
                return;
            }
        }
        this.process();
    }

    private void process(){
        LineItem removable = null;
        this.totalQantity = 0;
        this.totalPrice = 0;
        System.out.println(this.items);
        for(LineItem item : this.items){
            if(item.getQuantity() == 0){
                removable = item;
            }else{
                this.totalQantity += item.getQuantity();
                this.totalPrice += item.getQuantity() * item.getProduct().getDefaultPrice();
            }
        }
        if(removable != null){
            this.items.remove(removable);
        }
    }

    public int getTotalItemNumber(){
        return this.totalQantity;
    }

    public float getTotalPrice(){
        return this.totalPrice;
    }

    public HashMap toDict() {
        HashMap returnDict = new HashMap<>();
        ArrayList products = new ArrayList();
        for (LineItem item: items) {
            products.add(item.getProduct().toDict());
        }
        returnDict.put("products", products);
        returnDict.put("totalQuantity", getTotalItemNumber());
        returnDict.put("totalPrice", getTotalPrice());
        return returnDict;
    }

    public String toString(){
        return "Id: " + this.id + ", status: " + this.status + ", items: " + this.items;
    }
}

