package com.codecool.shop.dao;


import com.codecool.shop.dao.implementation.Cart;
import com.codecool.shop.model.Order;

import java.util.HashMap;

public interface OrderDao {

    void add(Order order);
    Order find(String email);
}
