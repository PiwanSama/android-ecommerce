/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.models.MapsModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {
    @SerializedName("overview_polyline")
    private OverviewPolyline overviewPolyLine;

    private List<Legs> legs;

    public OverviewPolyline getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public List<Legs> getLegs() {
        return legs;
    }
}
