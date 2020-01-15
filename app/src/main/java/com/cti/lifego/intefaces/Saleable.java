/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.intefaces;

import java.math.BigDecimal;

public interface Saleable {
    BigDecimal getPrice();
    String getName();
    int getID();
}
