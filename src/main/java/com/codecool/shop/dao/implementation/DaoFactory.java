package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.daojdbc.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.daojdbc.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.daojdbc.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.daomem.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.daomem.ProductDaoMem;
import com.codecool.shop.dao.implementation.daomem.SupplierDaoMem;

public class DaoFactory {

    public static String daoType;

    public ProductDao getProductDao() {
        if (daoType.equals("database")) {
            return new ProductDaoJdbc();
        }
        else {
            return ProductDaoMem.getInstance();
        }
    }

    public SupplierDao getSupplierDao() {
        if (daoType.equals("database")) {
            return new SupplierDaoJdbc();
        }
        else {
            return SupplierDaoMem.getInstance();
        }
    }

    public ProductCategoryDao getCategoryDao() {
        if (daoType.equals("database")) {
            return new ProductCategoryDaoJdbc();
        }
        else {
            return ProductCategoryDaoMem.getInstance();
        }
    }

}
