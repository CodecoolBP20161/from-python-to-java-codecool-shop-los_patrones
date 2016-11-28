package com.codecool.shop.dao.implementation.daojdbc;

import com.codecool.shop.model.ProductCategory;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryORM {

    private ArrayList<HashMap> queryData;

    CategoryORM (ArrayList data) {

        queryData = data;
    }

    public ArrayList<ProductCategory> buildCategoryObjects () {
        ArrayList<ProductCategory> categories = new ArrayList();
        for (HashMap aCategory : queryData) {
            ProductCategory category = new ProductCategory(
                    aCategory.get("name").toString(),
                    aCategory.get("department").toString(),
                    aCategory.get("description").toString());
            category.setId(Integer.parseInt(aCategory.get("id").toString()));
            categories.add(category);
        }
        return categories;
    }
}
