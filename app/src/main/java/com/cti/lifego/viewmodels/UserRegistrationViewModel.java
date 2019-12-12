/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
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
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()){
                    Log.e("API", "Insert Success"+response.body()+"XXX"+response.message());
                }
                else{
                    Log.e("API", "Insert Failed"+response.body()+"XXX"+response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, Throwable t) {
                Log.e("API", "Insert Failed");

            }
        });
    }

}
