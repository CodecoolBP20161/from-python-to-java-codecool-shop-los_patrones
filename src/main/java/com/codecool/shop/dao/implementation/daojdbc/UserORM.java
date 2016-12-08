package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by balint on 12/8/16.
 */
public class UserORM {

    ArrayList <HashMap> queryData;

    UserORM(ArrayList data) {
        this.queryData = data;
    }

    public ArrayList<User> buildUserObjects() {
        ArrayList<User> users = new ArrayList();
        for (HashMap aUser : queryData) {
            User user = new User(
                    aUser.get("name").toString(),
                    aUser.get("email").toString(),
                    aUser.get("password").toString());
            user.setID(Integer.parseInt(aUser.get("id").toString()));
            users.add(user);
        }
        return users;
    }
}
