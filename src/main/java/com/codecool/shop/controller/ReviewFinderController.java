package com.codecool.shop.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReviewFinderController {
    private static final String SERVICE_URL = "http://localhost:9876";


    public static String sendReview(spark.Request request) throws URISyntaxException, IOException {

        return execute("/api/review?title=" + request.queryParams("name"));
    }

    private static String execute(String url) throws IOException, URISyntaxException {
        URI uri = new URIBuilder(SERVICE_URL + url).build();
        return Request.Get(uri).execute().returnContent().asString();
    }

    public static HashMap<String, Object> parseReview(String s, spark.Request request) {
        HashMap<String, Object> mymap = new HashMap<>();
        ArrayList<JsonElement> mylist = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(s);
        JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
        Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
        for (Map.Entry<String, JsonElement> entry: entries) {
            JsonElement elem = parser.parse(String.valueOf(entry.getValue()));
            JsonObject objasd = elem.getAsJsonObject(); //since you know it's a JsonObject
            mylist.add(objasd.get("url"));
        }
        mymap.put("product", request.queryParams("name"));
        mymap.put("recommendations", mylist);
        return mymap;
    }
}