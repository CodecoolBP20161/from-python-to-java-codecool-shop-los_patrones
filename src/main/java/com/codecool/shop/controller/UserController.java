package com.codecool.shop.controller;

import spark.Request;
import spark.Response;

/**
 * Created by balint on 12/8/16.
 */
public class UserController {

    private Request request;
    private Response response;

    public UserController (Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public String manageRegProcess() {
        return "";
    }
}
