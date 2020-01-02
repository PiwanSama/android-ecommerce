/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cti.lifego.api.NetworkService;
import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private static ProductRepository instance;
    private NetworkService service = RetrofitInstance.getRetrofitInstance().create(NetworkService.class);

    public static ProductRepository getInstance(){
        if (instance==null){
            instance = new ProductRepository();
        }
        return instance;
    }

    public LiveData<List<Product>> listProducts(){
        final MutableLiveData<List<Product>> products = new MutableLiveData<>();
        service.getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        return products;
    }

    public MutableLiveData<Product> getProduct(String id){
        final MutableLiveData<Product> product = new MutableLiveData<>();
        service.getProduct(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
        return product;
    }

}