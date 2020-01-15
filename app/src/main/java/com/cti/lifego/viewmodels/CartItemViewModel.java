/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.Cart;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.utils.CartHelper;

import java.math.BigDecimal;

public class CartItemViewModel extends ViewModel {
    public MutableLiveData<Integer> productQuantity;
    public MutableLiveData<BigDecimal> productTotalCost;
    private CartItem cartItem;
    private Cart cart = CartHelper.getCart();

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public void increaseQuantity(CartItem cartItem){
        cart.update(cartItem.getProduct(), +1);
        productQuantity.setValue(cart.getQuantity(cartItem.getProduct()));
    }

    public void decreaseQuantity(CartItem cartItem){
        if (cart.getCartTotalQuantity()>1){
            cart.update(cartItem.getProduct(), -1);
            productQuantity.setValue(cart.getQuantity(cartItem.getProduct()));
        }
    }

    public void deleteItem(CartItem cartItem){
        cart.remove(cartItem.getProduct());
    }

    public MutableLiveData<BigDecimal> getProductTotalCost() {
        return productTotalCost;
    }

    public void setProductTotalCost(CartItem cartItem) {
        productTotalCost.setValue(cart.getCost(cartItem.getProduct()));
    }
}
