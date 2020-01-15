/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.models.MapsModels;


import com.google.gson.annotations.SerializedName;

public class OverviewPolyline {

    @SerializedName("points")
    public String points;

    public String getPoints() {
        return points;
    }
}
