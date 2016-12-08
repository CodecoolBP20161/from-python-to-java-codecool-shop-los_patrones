package com.codecool.shop.service;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.dao.implementation.daojdbc.UserDaoJdbc;
import com.codecool.shop.model.User;
import com.codecool.shop.util.EmailFacade;
import com.codecool.shop.util.HashFacade;
import com.google.gson.Gson;
import spark.Request;

import java.util.HashMap;

/**
 * Created by balint on 12/8/16.
 */
public class RegLogService {

    private Request request;
    private UserDao userDao;
    private EmailFacade emailManager;
    private Gson jsonBuilder;

    public RegLogService (Request request) {
        this.request = request;
        this.userDao = new UserDaoJdbc();
        this.emailManager = new EmailFacade();
        this.jsonBuilder = new Gson();
    }

    public String registerUser () {

        HashMap UserData = jsonBuilder.fromJson(request.body(), HashMap.class);

        if (!(userDao.findByEmail(UserData.get("email").toString()) == null)) {
            return "Email already in use";
        }

        String password = HashFacade.createHash(UserData.get("password").toString());
        User user = new User(
                UserData.get("name").toString(),
                UserData.get("email").toString(),
                password
        );
        userDao.add(user);
        emailManager.sendWelcome(user);

        return "Success";
    }
}
