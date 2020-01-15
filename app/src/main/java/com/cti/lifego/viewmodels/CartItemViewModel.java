/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.CartItem;
import com.cti.lifego.models.Product;

public class CartItemViewModel extends ViewModel {
    private CartItem cartItem;
    private Product product;

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public String getQuantityString(CartItem cartItem){
        return ("Qty: " + String.valueOf(cartItem.getQuantity()));
    }

    public void increaseQuantity(Context context){
        CartItem cartItem = getCartItem();
        cartItem.setQuantity(cartItem.getQuantity()+1);
        setCartItem(cartItem);
        product = cartItem.getProduct();
    }

    public void decreaseQuantity(Context context){
        CartItem cartItem = getCartItem();
        product = cartItem.getProduct();
        if (cartItem.getQuantity()>1){
            cartItem.setQuantity(cartItem.getQuantity()-1);
            setCartItem(cartItem);}
    }

    public void deleteItem(Context context){
        CartItem cartItem = getCartItem();
        product = cartItem.getProduct();
    }
}
