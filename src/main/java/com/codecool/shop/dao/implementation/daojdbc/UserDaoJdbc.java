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

    public UserDaoJdbc () {
        this.sqlHelper = new SqlFacade();
    }

    @Override
    public void add(User user) {
        String query = "Insert into users " +
                "(name, email, password) " +
                "values (" +
                "\'" + user.getName() + "\'" + ", " +
                "\'" + user.getEmail() + "\'" + ", " +
                "\'" + user.getPassword() + "\'" + ")";
        sqlHelper.executeUpdateQuery(query);
    }

    @Override
    public User find(int id) {
        String query = "Select * from users where id = " + id;
        ArrayList data = sqlHelper.executeSelectQuery(query);
        UserORM builder = new UserORM(data);
        return builder.buildUserObjects().get(0);
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public User findByEmail(String email) {
        String query = "Select * from users where email = \'" + email + "\'";
        ArrayList data = sqlHelper.executeSelectQuery(query);
        UserORM builder = new UserORM(data);
        ArrayList<User> users = builder.buildUserObjects();
        if (users.size() < 1) {
            return null;
        }
        return users.get(0);
    }
}
