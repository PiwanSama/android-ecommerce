/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsRetrofitInstance {

    public static Retrofit getRetrofitInstance(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/directions/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
