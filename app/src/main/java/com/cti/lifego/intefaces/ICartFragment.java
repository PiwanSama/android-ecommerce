/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.intefaces;

import com.cti.lifego.models.Product;

public interface ICartFragment {
    void updateQuantity(Product product, int quantity);
    void getShoppingCartList();
}
