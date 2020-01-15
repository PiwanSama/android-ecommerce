/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class PaymentOption {
    @SerializedName("id")
    public int id;
    @SerializedName("kin_name")
    public String name;
    @SerializedName("image")
    public int image;

    public PaymentOption(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
