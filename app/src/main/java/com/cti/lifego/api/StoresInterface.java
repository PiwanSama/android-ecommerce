/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.api;

import com.cti.lifego.models.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StoresInterface {
    @GET("stores")
    Call<List<Store>> getStores();
    @GET("stores/{id}")
    Call<Store>getStore(@Path("id") String id);
}
