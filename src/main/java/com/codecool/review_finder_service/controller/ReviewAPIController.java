package com.codecool.review_finder_service.controller;

import com.codecool.review_finder_service.service.APIService;
import spark.Request;
import spark.Response;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;

/**
 * Created by leviathan on 2017.01.03..
 */
public class ReviewAPIController {
    public static final String CATEGORY_PARAM_KEY = "title";
    private final APIService apiService;

    public ReviewAPIController(APIService apiService){
        this.apiService = apiService;
    }

    /*
    Uses the APIService's findReview and converts it to a JSON
    It returns this JSON as a String
     */
    public String getReviews(Request req, Response res){
        res.header("Access-Control-Allow-Origin", "*");
        try {
            String[][] reviews = apiService.findReviews(req.queryParams(CATEGORY_PARAM_KEY));
            JsonObjectBuilder builder = Json.createObjectBuilder();
            for (int i=0; i<reviews.length; i++){
                builder.add(Integer.toString(i), Json.createObjectBuilder()
                                                        .add("text", reviews[i][0])
                                                        .add("url", reviews[i][1])
                                                        .build());
            }
            return builder.build().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
