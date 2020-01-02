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
    private MutableLiveData<Product> selectedProduct = new MutableLiveData<>();
    private ProductRepository productRepository;
    private Product product;

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

    public MutableLiveData<Product> getProduct(String id){
        selectedProduct = productRepository.getProduct(id);
        return selectedProduct;
    }

    private LiveData<Product> getSelected(){
        return selectedProduct;
    }
}
