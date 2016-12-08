package com.codecool.shop.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SqlFacade {

    private static final String DATABASE = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "balint";
    private static final String DB_PASSWORD = "Fuzzwktrka88bcykjypt";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }

    public int executeUpdateQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement()
        ){
            statement.executeUpdate(query, statement.RETURN_GENERATED_KEYS);
            ResultSet result = statement.getGeneratedKeys();
            int setId = 0;
            while (result.next()) {
                setId = Integer.parseInt(result.getArray(1).toString());
            }
            statement.close();
            connection.close();
            return setId;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int executeUserUpdate(String pst, String name, String email, String pw) {
        String query = pst;

        try (
             PreparedStatement statement = getConnection().prepareStatement(query,
             Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, pw);
            System.out.println(statement.toString());

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    System.out.println(1);
    //              return generatedKeys.getInt(1);
                    return 1;
                }
            } catch (Exception e) {
                return 0;
            }
        } catch (SQLException e) {
               return 0;
        }
        return 0;
    }

    public ArrayList executeSelectQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement()
        ){
            ResultSet result = statement.executeQuery(query);
            ArrayList data = new ArrayList();
            while (result.next()) {
                HashMap row = new HashMap();
                for (int i=1; i<=result.getMetaData().getColumnCount(); i++) {
                    row.put(result.getMetaData().getColumnName(i), result.getArray(i));
                }
                data.add(row);
            }
            result.close();
            statement.close();
            connection.close();
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
