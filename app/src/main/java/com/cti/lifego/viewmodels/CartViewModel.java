/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private List<CartItem> cartList = new ArrayList<>();

    public List<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartItem> cartList) {
        this.cartList = cartList;
    }

    private String getProductQuantitiesString(){
        int totalItems = 0;
        for (CartItem cartItem : cartList){
            totalItems += cartItem.getQuantity();
        }
        String s = "";
        if (totalItems>1){
            s = "items";
        }
        else {
            s = "item";
        }
        return (String.valueOf(totalItems)+" "+ s);
    }

    public void getTotalCostString(){
        int totalCost = 0;
        for (CartItem cartItem: cartList){
            int productQuantity = cartItem.getQuantity();

        }
    }
}
