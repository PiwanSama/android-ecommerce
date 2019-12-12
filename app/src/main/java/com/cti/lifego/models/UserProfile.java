/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    @SerializedName("id")
    public int id;
    @SerializedName("address")
    public String address;
}
