package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DaoFactory;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductORM {

    private ArrayList<HashMap> queryData;

    ProductORM (ArrayList data) {
        queryData = data;
    }

    public ArrayList<Product> buildProductObjects () {
        ArrayList<Product> products = new ArrayList();
        for (int i=0; i<queryData.size(); i++) {
            DaoFactory factory = new DaoFactory();
            ProductCategoryDao pDao =factory.getCategoryDao();
            ProductCategory category = pDao.find(Integer.parseInt(queryData.get(i).get("category").toString()));
            SupplierDao sDao = factory.getSupplierDao();
            Supplier supplier = sDao.find(Integer.parseInt(queryData.get(i).get("supplier").toString()));
            Product product = new Product(
                    queryData.get(i).get("name").toString(),
//                    Float.parseFloat(queryData.get(i).get("price").toString()),
                    1,
                    queryData.get(i).get("currency").toString(),
                    queryData.get(i).get("description").toString(),
                    category,
                    supplier);
            product.setId(Integer.parseInt(queryData.get(i).get("id").toString()));
            products.add(product);
        }
        return products;
    }
}
