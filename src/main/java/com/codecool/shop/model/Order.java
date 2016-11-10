package com.codecool.shop.model;


import com.codecool.shop.dao.implementation.OrderDaoMem;

public class Order extends BaseModel {

    Cart cart;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String billingAddress;
    String shippingAddress;

    public Order(int id, Cart cart, String firstName, String lastName, String email,
                 String phoneNumber, String billingAddress, String shippingAddress) {
        super(id);
        this.cart = cart;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        OrderDaoMem.getInstance().add(this);

    }
}
