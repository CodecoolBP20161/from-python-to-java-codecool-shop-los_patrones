package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.util.SqlFacade;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoJdbc implements ProductCategoryDao {

    SqlFacade sqlHelper;

    public ProductCategoryDaoJdbc()  {
        this.sqlHelper = new SqlFacade();
    }

    @Override
    public void add(ProductCategory category) {
        String query =
                "Insert into category" +
                        "(name, department, description)" +
                        "values (" +
                        "\'" + category.getName() + "\'" + "," +
                        "\'" + category.getDepartment() + "\'" + "," +
                        "\'" + category.getDescription() + "\'" + ")";

        int Id = sqlHelper.executeUpdateQuery(query);
        category.setId(Id);
    }

    @Override
    public ProductCategory find(int id) {
        String query = "Select * from category where id = " + id;
        ArrayList data = sqlHelper.executeSelectQuery(query);
        CategoryORM builder = new CategoryORM(data);
        ProductCategory category = builder.buildCategoryObjects().get(0);
        return category;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        String query = "Select * from category";
        ArrayList data = sqlHelper.executeSelectQuery(query);
        CategoryORM builder = new CategoryORM(data);
        return builder.buildCategoryObjects();
    }
}
