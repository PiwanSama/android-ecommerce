/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.CartItem;
import com.cti.lifego.utils.Prices;

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

    public String getTotalCostString(){
        double totalCost = 0;
        for (CartItem cartItem: cartList){
            int productQuantity = cartItem.getQuantity();
            double cost = productQuantity * (Prices.getPrices().get(cartItem.getProduct().getId())).doubleValue();
        }
        return "s";
    }
}
