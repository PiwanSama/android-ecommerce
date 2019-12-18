package com.cti.lifego.utils;

import com.cti.lifego.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Prices {

    private static final HashMap<String, Integer> PRICES;
    private static final List<Product> products;
    static
    {
        PRICES = new HashMap<String, Integer>();
        products = new ArrayList<>();
        for(Product product : products){
            PRICES.put(String.valueOf(product.getId()), product.getPrice());
        }
    }

    public static HashMap<String, Integer> getPrices(){
        return  PRICES;
    }
}