/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.models.MapsModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionResults {
    @SerializedName("routes")
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }
}
