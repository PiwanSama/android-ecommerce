/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private MutableLiveData<String> cartItemCount = new MutableLiveData<>();
    private MutableLiveData<String> cartItemCost = new MutableLiveData<>();
    private List<CartItem> cartList = new ArrayList<>();
    private MutableLiveData<String> productQty;

    public List<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartItem> cartList) {
        this.cartList = cartList;
    }

    public MutableLiveData<String> getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount() {
        cartItemCount.setValue(String.valueOf(cartList.size()));
    }

    public MutableLiveData<String> getCartItemCost() {
        return cartItemCost;
    }

    public void setCartItemCost(MutableLiveData<String> cartItemCost) {
        this.cartItemCost = cartItemCost;
    }

    public void setCartItemCount(MutableLiveData<String> cartItemCount) {
        this.cartItemCount = cartItemCount;
    }

    public MutableLiveData<String> getProductQty() {
        return productQty;
    }

    public void setProductQty(MutableLiveData<String> productQty) {
        this.productQty = productQty;
    }
}
