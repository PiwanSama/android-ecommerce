/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.api.UserService;
import com.cti.lifego.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationViewModel extends ViewModel {

    public void createUser(User user) {

        UserService service = RetrofitInstance.getRetrofitInstance().create(UserService.class);

        Call<User> call = service.createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("API", "Insert Success");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("API", "Insert Failed");

            }
        });
    }

}
