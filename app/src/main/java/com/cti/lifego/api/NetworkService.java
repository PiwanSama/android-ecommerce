/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.api;

import com.cti.lifego.models.Order;
import com.cti.lifego.models.Product;
import com.cti.lifego.models.Store;
import com.cti.lifego.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface NetworkService {
    //Users
    @POST("register")
    Call<ResponseBody> createUser(@Body User user);
    @POST("login")
    Call<ResponseBody> loginUser(@Field("email") String email, @Field("password") String password);
    @GET("user")
    Call<User> getUser(@Path("id") String userID);
    @PATCH("update_user/{id}")
    Call<User> updateUser(@Body User user, @Path("id") String UserID);

    //Stores
    @GET("stores/{id}")
    Call<List<Store>> getStores(@Path("id") String categoryID);

    //Products
    @GET("products")
    Call<List<Product>> getProducts(@Path("storeID") String storeID);
    @GET("products/{id}")
    Call<Product>getProduct(@Path("id") String productID);

    //Orders
    @GET("orders")
    Call<List<Order>> getOrders();
    @GET("order/{id}")
    Call<Order>getOrder(@Path("id") String OrderID);

    //Google Map API
    @GET
    Call<String> getDirection(@Url String url);
}
