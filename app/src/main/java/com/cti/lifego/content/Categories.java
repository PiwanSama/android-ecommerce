/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.content;

import com.cti.lifego.R;
import com.cti.lifego.models.Category;

import java.util.HashMap;

public class Categories {

    public Category[] CATEGORIES = {GROCERIES,FRESH_PRODUCE,PHARMACIES,AGRO_EQUPMENT,AGRO_SUPPLIES};

    public HashMap<String, Category> CATEGORY_MAP = new HashMap<>();

    public Categories() {
        for(Category category : CATEGORIES){
            CATEGORY_MAP.put(String.valueOf(category.getId()), category);
        }

    }

    public static final Category GROCERIES = new Category(1,"Groceries", "Stock your home with groceries from our leading stores", R.drawable.farm_produce);

    public static final Category FRESH_PRODUCE = new Category(2,"Fresh Produce", "Farm fresh fruits and vegetables straight to you", R.drawable.groceries);

    public static final Category PHARMACIES = new Category(3,"Pharmacies", "Pharmacueticals for your health and wellbeing", R.drawable.pharmacies);

    public static final Category AGRO_EQUPMENT = new Category(4,"Agricultural Equipment", "Equipment to get your farm up and running", R.drawable.agro_equipment);

    public static final Category AGRO_SUPPLIES = new Category(5,"Agricultural Supplies", "Quality seed and agro input for your farm", R.drawable.agro_supplies);
}
