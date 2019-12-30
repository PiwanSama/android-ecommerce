/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.cti.lifego.intefaces.ICartItem;
import com.cti.lifego.models.CartItem;

public class CartItemViewModel extends ViewModel {
    private CartItem cartItem;

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
        ICartItem iCartItem = (ICartItem)context;
        iCartItem.updateQuantity(cartItem.getProduct(), 1);
    }

    public void decreaseQuantity(Context context){
        CartItem cartItem = getCartItem();
        if (cartItem.getQuantity()>1){
            cartItem.setQuantity(cartItem.getQuantity()-1);
            setCartItem(cartItem);
            ICartItem iCartItem = (ICartItem)context;
            iCartItem.updateQuantity(cartItem.getProduct(), -1);
        }
    }

    public void deleteItem(Context context){
        CartItem cartItem = getCartItem();
        ICartItem iCartItem = (ICartItem) context;
        iCartItem.deleteItem(cartItem.getProduct());
    }
}
