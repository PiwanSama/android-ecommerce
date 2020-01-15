/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.utils;

import com.cti.lifego.models.Cart;

public class CartHelper {
    private static Cart cart = new Cart();

    /**
     * Retrieve the shopping cart. Call this before perform any manipulation on the shopping cart.
     *
     * @return the shopping cart
     */

    public static Cart getCart(){
        if (cart == null){
            cart = new Cart();
        }
        return cart;
    }
}
