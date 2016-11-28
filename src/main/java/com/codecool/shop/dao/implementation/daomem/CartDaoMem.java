package com.codecool.shop.dao.implementation.daomem;


import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartDaoMem implements CartDao {

    private static List<Cart> DATA = new ArrayList<>();
    private static CartDaoMem instance = null;

    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        cart.setId(DATA.size() + 1);
        DATA.add(cart);
    }

    @Override
    public Cart find(int id) {
        for(Cart cart : this.DATA){
            if(cart.getId() == id){
                return cart;
            }
        }
        return null;
    }
}
