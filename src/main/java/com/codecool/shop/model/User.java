package com.codecool.shop.model;

/**
 * Created by balint on 12/8/16.
 */
public class User {

    private String name;
    private String email;
    private String password;
    private int ID;

    public User (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
