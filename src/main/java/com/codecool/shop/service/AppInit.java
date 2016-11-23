package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DaoFactory;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.util.SqlFacade;

public class AppInit {

    public void initApp (String dataBaseType) {
        DaoFactory.daoType = dataBaseType;
        if (DaoFactory.daoType.equals("database")) {
            createTables();
        }
        populateData();
    }

    private void createTables () {
        SqlFacade sqlHelper = new SqlFacade();

        String createCategoryTable =
                "CREATE TABLE IF NOT EXISTS CATEGORY" +
                "(ID serial primary key," +
                "Name text not null," +
                "department text not null," +
                "description text not null)";

        String createSupplierTable =
                "CREATE TABLE IF NOT EXISTS SUPPLIER" +
                "(ID serial primary key," +
                "Name text not null," +
                "description text not null)";

        String createProductTable =
                "CREATE TABLE IF NOT EXISTS PRODUCTS" +
                "(ID serial primary key," +
                "Name text not null," +
                "Price text not null," +
                "Currency text not null," +
                "category int references CATEGORY(ID)," +
                "supplier int references SUPPLIER(ID)," +
                "description text not null)";

        sqlHelper.executeUpdateQuery(createCategoryTable);
        sqlHelper.executeUpdateQuery(createSupplierTable);
        sqlHelper.executeUpdateQuery(createProductTable);
    }

    private static Boolean isEmpty () {
        SqlFacade sqlHelper = new SqlFacade();
        return sqlHelper.executeSelectQuery("select * from products").isEmpty();
    }

    private static void populateData(){

        if (!isEmpty() && DaoFactory.daoType.equals("database")) {
            return;
        }

        DaoFactory factory = new DaoFactory();

        //setting up a new supplier
        SupplierDao supplierDataStore = factory.getSupplierDao();
        Supplier amazon = new Supplier("Amazon", "Digital content and com.codecool.shop.service");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier stuff = new Supplier("Stuff", "Stuff");
        supplierDataStore.add(stuff);

        //setting up a new product category
        ProductCategoryDao productCategoryDataStore = factory.getCategoryDao();
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(laptop);
        ProductCategory amazing = new ProductCategory("Amazing", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(amazing);

        //setting up products and printing it
        ProductDao productDataStore = factory.getProductDao();
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazons latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));

        productDataStore.add(new Product("Amazon Fire 222", 89, "USD", "Amazons latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Whatever", 10000, "USD", "Whatever", laptop, amazon));
        productDataStore.add(new Product("Beni", 1.99f, "USD", "such Beni, much Vörös", amazing, stuff));
    }
}
