package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;
import com.codecool.shop.util.SqlFacade;

import java.util.ArrayList;

/**
 * Created by balint on 12/8/16.
 */
public class UserDaoJdbc implements UserDao {

    SqlFacade sqlHelper;

    @Override
    public void add(User user) {
        String query = "insert into user values(?,?,?)";
        int Id = sqlHelper.executeUserUpdate(query, user.getName(), user.getEmail(), user.getPassword());
        user.setID(Id);
    }

    @Override
    public User find(int id) {
        String query = "Select * from user where id = " + id;
        ArrayList data = sqlHelper.executeSelectQuery(query);
        UserORM builder = new UserORM(data);
        return builder.buildUserObjects().get(0);
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public User findByEmail(String email) {
        String query = "Select * from user where email = " + email;
        try {
            ArrayList data = sqlHelper.executeSelectQuery(query);
            UserORM builder = new UserORM(data);
            return builder.buildUserObjects().get(0);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
