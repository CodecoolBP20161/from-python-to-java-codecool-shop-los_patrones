package com.codecool.shop.dao.implementation;

import java.util.ArrayList;


public class Cart {
    private ArrayList<LineItem> items = new ArrayList<>();
    private String status;
    private int id;
    private static int counter = 0;
    private int totalQantity;
    private float totalPrice;
    private static Cart instance = null;

    Cart(){
        this.id = ++counter;
        this.status = "New";
        this.totalQantity = 0;
        this.totalPrice = 0;
    }

    public String increaseQuantity(int i){
        System.out.println(this.items);
        try{
            this.items.get(i).incrementQuantity();
            return "Successful";
        }

        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
            return "No such LineItem exists!";
        }


    }

    public void decreaseQuantity(int i){
        this.items.get(i).decrementQuantity();
    }

    public ArrayList<LineItem> getItems() {
        return items;
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void add(LineItem item){
        for(LineItem currentItem : this.items){
            if(item.getProduct().getName() == currentItem.getProduct().getName()){
                System.out.println("add");
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
            if(item.getProduct().getName() == currentItem.getProduct().getName()){
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

    public void checkOut(){
        this.status = "Checked out";
    }

    public String toString(){
        return "Id: " + this.id + ", status: " + this.status + ", items: " + this.items;
    }
}

