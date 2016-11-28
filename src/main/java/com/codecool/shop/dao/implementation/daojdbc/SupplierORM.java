package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.HashMap;


public class SupplierORM {

    private ArrayList<HashMap> queryData;

    public SupplierORM(ArrayList data) {

        queryData = data;
    }

    public ArrayList<Supplier> buildSupplierObjects() {
        ArrayList<Supplier> suppliers = new ArrayList();
        for (HashMap aSupplier : queryData) {
            Supplier supplier = new Supplier(
                    aSupplier.get("name").toString(),
                    aSupplier.get("description").toString());
            supplier.setId(Integer.parseInt(aSupplier.get("id").toString()));
            suppliers.add(supplier);
        }
        return suppliers;
    }
}