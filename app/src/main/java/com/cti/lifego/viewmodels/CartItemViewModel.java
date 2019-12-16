/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.content.Context;

import com.cti.lifego.intefaces.ICartFragment;
import com.cti.lifego.models.CartItem;

public class CartItemViewModel {
    private CartItem cartItem;

    public CartItem getCartItem() {
        return cartItem;
    }

    private void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public void increaseQuantity(Context context){
        CartItem cartItem = getCartItem();
        cartItem.setQuantity(cartItem.getQuantity()+1);
        setCartItem(cartItem);
        ICartFragment iCartFragment = (ICartFragment)context;
        iCartFragment.updateQuantity(cartItem.getProduct(), 1);
    }

    public void decreaseQuantity(Context context){
        CartItem cartItem = getCartItem();
        if (cartItem.getQuantity()>1){
            cartItem.setQuantity(cartItem.getQuantity()-1);
            setCartItem(cartItem);
            ICartFragment iCartFragment = (ICartFragment)context;
            iCartFragment.updateQuantity(cartItem.getProduct(), -1);
        }
    }
}
