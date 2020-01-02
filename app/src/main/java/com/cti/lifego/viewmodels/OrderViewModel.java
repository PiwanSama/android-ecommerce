/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.Order;
import com.cti.lifego.repositories.OrderRepository;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private LiveData<List<Order>> products;
    private MutableLiveData<Order> selectedOrder = new MutableLiveData<>();
    private OrderRepository orderRepository;
    private Order product;

    public void init(){
        if (products!=null){
            return;
        }
        orderRepository = OrderRepository.getInstance();
        products = orderRepository.listOrders();
    }

    public LiveData<List<Order>> listOrders(){
        return products;
    }

    public MutableLiveData<Order> getOrder(String id){
        selectedOrder = orderRepository.getOrder(id);
        return selectedOrder;
    }

    private LiveData<Order> getSelected(){
        return selectedOrder;
    }
}
