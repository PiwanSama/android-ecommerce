/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.CartItem;
import com.cti.lifego.models.Store;
import com.cti.lifego.repositories.OrderRepository;

import java.util.List;

public class CheckoutViewModel extends ViewModel {

    public OrderRepository orderRepository;
    public MutableLiveData<String> delivery_address = new MutableLiveData<>();
    public MutableLiveData<String> totalCost = new MutableLiveData<>();
    public MutableLiveData<String> totalFee = new MutableLiveData<>();
    public MutableLiveData<String> order_date = new MutableLiveData<>();
    public MutableLiveData<String> delivery_date = new MutableLiveData<>();
    public MutableLiveData<String> order_time = new MutableLiveData<>();
    public MutableLiveData<String> quantity = new MutableLiveData<>();
    public MutableLiveData<String> status = new MutableLiveData<>();
    public MutableLiveData<Store> store = new MutableLiveData<>();
    public MutableLiveData<String> image = new MutableLiveData<>();

    public MutableLiveData<String> delivery_fee = new MutableLiveData<>();

    public MutableLiveData<List<CartItem>> orderList = new MutableLiveData<>();

    public MutableLiveData<CheckoutState> checkout_state = new MutableLiveData<>();

    private MutableLiveData<Boolean> requires_prescription = new MutableLiveData<>();

    public enum CheckoutState{
        CHECKOUT_LOCATION,
        CHECKOUT_SUMMARY,
        CHECKOUT_PAYMENT
    }

    public MutableLiveData<CheckoutState> getCheckout_state() {
        return checkout_state;
    }

    public void setCheckout_state(MutableLiveData<CheckoutState> checkout_state) {
        this.checkout_state = checkout_state;
    }

    public MutableLiveData<Boolean> getRequires_prescription() {
        return requires_prescription;
    }

    public void setRequires_prescription(MutableLiveData<Boolean> requires_prescription) {
        this.requires_prescription = requires_prescription;
    }

    public void uploadImage(String filePath){

    }


}

//Todo change photo user
