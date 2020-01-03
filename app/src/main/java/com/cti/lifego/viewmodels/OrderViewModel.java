/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.Order;
import com.cti.lifego.models.User;
import com.cti.lifego.repositories.OrderRepository;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private LiveData<List<Order>> orders;
    private MutableLiveData<String> selectedOrderId = new MutableLiveData<>();
    private MutableLiveData<OrderViewState> viewState = new MutableLiveData<>();
    private OrderRepository orderRepository;

    public enum OrderViewState{
        VIEW_ALL_ORDERS,
        VIEW_SINGLE_ORDER
    }

    public OrderViewModel() {
        viewState.setValue(OrderViewState.VIEW_ALL_ORDERS);
    }

    public void init(){
        if (orders!=null){
            return;
        }
        User user = new User();
        orderRepository = OrderRepository.getInstance();
        orders = orderRepository.listOrders(String.valueOf(user.getId()));
    }

    public MutableLiveData<OrderViewState> getViewState() {
        return viewState;
    }

    public void select(String id) {
        selectedOrderId.setValue(id);
        viewState.setValue(OrderViewState.VIEW_SINGLE_ORDER);
    }

    public LiveData<String> getSelected(){
        return selectedOrderId;
    }

    public LiveData<List<Order>> listOrders(){
        return orders;
    }

    public MutableLiveData<Order> getOrder(String id){
        return orderRepository.getOrder(id);
    }

}
