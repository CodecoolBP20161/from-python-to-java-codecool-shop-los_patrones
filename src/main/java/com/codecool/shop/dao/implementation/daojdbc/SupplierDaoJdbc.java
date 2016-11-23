package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.util.SqlFacade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by balint on 22.11.16.
 */
public class SupplierDaoJdbc implements SupplierDao {

    SqlFacade sqlHelper;

    public SupplierDaoJdbc()  {
        this.sqlHelper = new SqlFacade();
    }

    @Override
    public void add(Supplier supplier) {
        String query =
                "Insert into supplier " +
                "(name, description) " +
                "values (" +
                "\'" + supplier.getName() + "\'" + ", " +
                "\'" + supplier.getDescription() + "\'" + ")";

        int Id = sqlHelper.executeUpdateQuery(query);
        supplier.setId(Id);
    }

    @Override
    public Supplier find(int id) {
        String query = "Select * from supplier where id = " + id;
        ArrayList data = sqlHelper.executeSelectQuery(query);
        SupplierORM builder = new SupplierORM(data);
        return builder.buildSupplierObjects().get(0);
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Supplier> getAll() {
        String query = "Select * from supplier";
        ArrayList data = sqlHelper.executeSelectQuery(query);
        SupplierORM builder = new SupplierORM(data);
        return builder.buildSupplierObjects();
    }
}
