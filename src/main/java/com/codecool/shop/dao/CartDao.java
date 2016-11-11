package com.codecool.shop.dao;


import com.codecool.shop.model.Cart;

public interface CartDao {
    void add(Cart cart);
    Cart find(int id);
}
