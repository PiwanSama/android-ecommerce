/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.models.MapsModels;

public class Steps {
    private Location start_location;
    private Location end_location;
    private OverviewPolyline polyline;
    private Duration duration;
    private Distance distance;

    public Location getStart_location() {
        return start_location;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public OverviewPolyline getPolyline() {
        return polyline;
    }

    public Duration getDuration() { return duration; }

    public Distance getDistance() { return distance; }
}