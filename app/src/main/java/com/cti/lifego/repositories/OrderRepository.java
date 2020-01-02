/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cti.lifego.api.NetworkService;
import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    private static OrderRepository instance;
    private NetworkService service = RetrofitInstance.getRetrofitInstance().create(NetworkService.class);

    public static OrderRepository getInstance(){
        if (instance==null){
            instance = new OrderRepository();
        }
        return instance;
    }

    public LiveData<List<Order>> listOrders(){
        final MutableLiveData<List<Order>> orders = new MutableLiveData<>();
        service.getOrders().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                orders.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
        return orders;
    }

    public MutableLiveData<Order> getOrder(String id){
        final MutableLiveData<Order> order = new MutableLiveData<>();
        service.getOrder(id).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                order.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
        return order;
    }

}