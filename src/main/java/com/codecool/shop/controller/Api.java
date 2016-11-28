package com.codecool.shop.controller;

import com.google.gson.Gson;
import spark.Request;

import java.util.HashMap;

public abstract class Api {

    public static String stringify (Object model) {
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    public static HashMap parse (Request request) {
        Gson gson = new Gson();
        return gson.fromJson(request.body(), HashMap.class);
    }

}