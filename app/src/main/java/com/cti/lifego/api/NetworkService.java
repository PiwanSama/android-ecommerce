/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.api;

import com.cti.lifego.models.Order;
import com.cti.lifego.models.Product;
import com.cti.lifego.models.Store;
import com.cti.lifego.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkService {
    //Users
    @POST("register")
    Call<User> createUser(@Body User user);
    @GET("user")
    Call<User> getUser(@Path("id") String id);
    @PATCH("update_user/{id}")
    Call<User> updateUser(@Body User user, @Path("id") String id);

    //Stores
    @GET("stores")
    Call<List<Store>> getStores();
    @GET("stores/{id}")
    Call<Store>getStore(@Path("id") String id);

    //Products
    @GET("products")
    Call<List<Product>> getProducts();
    @GET("stores/{id}")
    Call<Product>getProduct(@Path("id") String id);

    //Orders
    @GET("orders")
    Call<List<Order>> getOrders();
    @GET("stores/{id}")
    Call<Product>getOrder(@Path("id") String id);
}
