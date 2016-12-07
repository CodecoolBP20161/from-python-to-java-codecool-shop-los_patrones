package com.codecool.shop.dao;

import com.codecool.shop.model.User;

/**
 * Created by balint on 12/7/16.
 */
public interface UserDao {

    void add(User user);
    User find(int id);
    void remove(int id);
}
