/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.content;

public final class Constants {

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME = "com.cti.lifego";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    private static final String fcm_url = "https://fcm.googleapis.com";

    private static double BASE_FARE = 500;
    private static double TIME_RATE = 0.35;
    private static double DISTANCE_RATE = 1.75;

    public static double getPrice(double km, int time){
        return (BASE_FARE + (TIME_RATE * time) + (DISTANCE_RATE * km));
    }

}
