//package com.codecool.shop.service;
//
//import com.codecool.shop.model.User;
//import com.google.gson.Gson;
//import spark.Request;
//
//import java.util.HashMap;
//
///**
// * Created by balint on 12/7/16.
// */
//public class UserService {
//
//    private Request request;
//    private HashFacade hashHelper;
//    private EmailFacade emailHelper;
//    private Gson JSONBuilder;
//    private UserDAO userDAO;
//
//    public UserService (Request request) {
//        this.request = request;
//        this.hashHelper = new hashFacade();
//        this.emailHelper = new emailFacade();
//        this.JSONBuilder = new Gson();
//        this.userDAO = new UserDAO();
//    }
//
//    public String register() {
//        HashMap requestData = JSONBuilder.fromJson(request.body(), HashMap.class);
//
//        if (!(userDAO.findByEmail(requestData.get("email")) == null)) {
//            return "Email in use";
//        }
//
//        String password = hashHelper.createHash(requestData.get("password"));
//        User createdUser = userDAO.add(requestData.get("username"), requestData.get("email"), password);
//        emailHelper.sendMail(createdUser);
//
//        return "Success";
//    }
//}
