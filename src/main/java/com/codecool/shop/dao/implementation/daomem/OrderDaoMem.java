package com.codecool.shop.dao.implementation.daomem;


import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {

    private static List<Order> DATA = new ArrayList<>();
    private static OrderDaoMem instance = null;

    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        order.setId(DATA.size() + 1);
        DATA.add(order);
    }

    @Override
    public Order find(String email) {
        for(Order order : this.DATA){
            if(order.getEmail() == email){
                return order;
            }
        }
        return null;
    }

}
