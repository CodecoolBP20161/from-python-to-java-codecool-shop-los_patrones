package com.codecool.shop.controller;

import org.apache.http.client.utils.URIBuilder;

import org.apache.http.client.fluent.Request;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ReviewFinderController {
    private static final String SERVICE_URL = "http://localhost:9876";


    public static String sendReview(spark.Request request) throws URISyntaxException, IOException {

        return execute("/api/review?title=" + request.queryParams("name"));
    }

    private static String execute(String url) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(SERVICE_URL + url).build();
        return Request.Get(uri).execute().returnContent().asString();
    }
}