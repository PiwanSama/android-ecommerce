/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.api;

import com.cti.lifego.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @POST("register")
    Call<User> createUser(@Body User user);
}
