/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.Cart;
import com.cti.lifego.utils.CartHelper;

import java.math.BigDecimal;

public class FloatingOrderViewModel extends ViewModel {

    private String cartItemCount;
    private BigDecimal totalPrice;

    private Cart cart = CartHelper.getCart();

    public void setCartItemCount(String cartItemCount) {
        this.cartItemCount = cartItemCount;
    }

    public BigDecimal getTotalPrice() {
        return cart.getCartTotal();
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getCartItemCount(){
        return String.valueOf(cart.getCartTotalQuantity());
    }

}
