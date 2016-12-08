package com.codecool.shop.dao;

import com.codecool.shop.model.User;

/**
 * Created by balint on 12/8/16.
 */
public interface UserDao {

    void add(User product);
    User find(int id);
    void remove(int id);
}
