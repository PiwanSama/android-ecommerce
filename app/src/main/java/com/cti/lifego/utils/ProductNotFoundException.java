/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.utils;

public class ProductNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 43L;

    private static final String DEFAULT_MESSAGE = "Product is not found in the shopping cart.";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
