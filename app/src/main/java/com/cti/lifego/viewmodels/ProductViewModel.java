/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.LiveData;

import com.cti.lifego.models.Product;
import com.cti.lifego.repositories.ProductRepository;

import java.util.List;

public class ProductViewModel {
    private LiveData<List<Product>> products;
    private ProductRepository productRepository;

    public void init(){
        if (products!=null){
            return;
        }
        productRepository = ProductRepository.getInstance();
        products = productRepository.listProducts();
    }

    public LiveData<List<Product>> listProducts(){
        return products;
    }

    public LiveData<Product> getProduct(String id){
        LiveData<Product> product = productRepository.getProduct(id);
        return product;
    }

}
