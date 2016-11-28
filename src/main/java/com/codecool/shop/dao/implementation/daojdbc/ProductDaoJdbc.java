package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.util.SqlFacade;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    SqlFacade sqlHelper;

    public ProductDaoJdbc() {
        this.sqlHelper = new SqlFacade();
    }

    @Override
    public void add(Product product) {
        String query =
                "Insert into products" +
                "(name, price, currency, category, supplier, description)" +
                "values (" +
                "\'" + product.getName() + "\'" + "," +
                "\'" + product.getPrice() + "\'" + "," +
                "\'" + product.getDefaultCurrency().toString() + "\'" + "," +
                "\'" + product.getProductCategory().getId() + "\'" + "," +
                "\'" + product.getSupplier().getId() + "\'" + "," +
                "\'" + product.getDescription() + "\'" + ")";

        sqlHelper.executeUpdateQuery(query);
    }

    @Override
    public Product find(int id) {
        String query = "Select * from products where id = " + id;
        ArrayList data = sqlHelper.executeSelectQuery(query);
        ProductORM builder = new ProductORM(data);
        Product product = builder.buildProductObjects().get(0);
        return product;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        String query = "Select * from products";
        ArrayList data = sqlHelper.executeSelectQuery(query);
        ProductORM builder = new ProductORM(data);
        return builder.buildProductObjects();
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = "Select * from products where supplier = " + supplier.getId();
        ArrayList data = sqlHelper.executeSelectQuery(query);
        ProductORM builder = new ProductORM(data);
        return builder.buildProductObjects();
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = "Select * from products where category = " + productCategory.getId();
        ArrayList data = sqlHelper.executeSelectQuery(query);
        ProductORM builder = new ProductORM(data);
        return builder.buildProductObjects();
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory, Supplier supplier) {
        String query = "Select * from products where category = " + productCategory.getId() + " and supplier = " + supplier.getId();
        ArrayList data = sqlHelper.executeSelectQuery(query);
        ProductORM builder = new ProductORM(data);
        return builder.buildProductObjects();
    }
}
