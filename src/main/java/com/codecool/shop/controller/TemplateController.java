package com.codecool.shop.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class TemplateController {

    public static ModelAndView renderIndex(Request req, Response res) {
        HashMap params = new HashMap();
        System.out.println("bela");
        return new ModelAndView(params, "product/app");
    }

    public static ModelAndView renderPay(Request req, Response res) {
        HashMap params = new HashMap();
        return new ModelAndView(params, "product/pay");
    }
}
