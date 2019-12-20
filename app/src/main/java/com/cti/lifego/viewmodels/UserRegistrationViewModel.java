/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.api.NetworkService;
import com.cti.lifego.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationViewModel extends ViewModel {

    public void createUser(User user) {

        NetworkService service = RetrofitInstance.getRetrofitInstance().create(NetworkService.class);

        Call<ResponseBody> call = service.createUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.e("API", "Insert Success"+response.body()+"XXX"+response.message());
                }
                else{
                    Log.e("API", "Insert Failed"+response.body()+"XXX"+response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Log.e("API", "Insert Failed");

            }
        });
    }

}
