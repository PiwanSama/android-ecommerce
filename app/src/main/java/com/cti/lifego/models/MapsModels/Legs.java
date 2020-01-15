/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.models.MapsModels;

import java.util.List;

public class Legs {
    private List<Steps> steps;
    private Duration duration;
    private Distance distance;

    public List<Steps> getSteps() {
        return steps;
    }

    public Duration getDuration() { return duration; }

    public Distance getDistance() { return distance; }
}