/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.Product;
import com.cti.lifego.repositories.ProductRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private LiveData<List<Product>> products;
    private MutableLiveData<String> storeID = new MutableLiveData<>();

    public void init(){
        if (products!=null){
            return;
        }
        ProductRepository productRepository = ProductRepository.getInstance();
        products = productRepository.listProducts(storeID.getValue());
    }

    public LiveData<List<Product>> listProducts(String storeID){
        return products;
    }

}
