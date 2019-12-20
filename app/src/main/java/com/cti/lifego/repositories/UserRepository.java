/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cti.lifego.api.NetworkService;
import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.models.LoginUser;
import com.cti.lifego.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository{
    private static UserRepository instance;
    private NetworkService service = RetrofitInstance.getRetrofitInstance().create(NetworkService.class);

    public static UserRepository getInstance(){
        if (instance==null){
            instance = new UserRepository();
        }
        return instance;
    }

    public Call<ResponseBody> loginUser(LoginUser user){
        Call<ResponseBody> call = service.loginUser(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    String responseString = response.body().toString();
                    Log.i("API", "Login Success" + responseString + " " + response.message());
                }
                else {
                    String errorString = response.errorBody().toString();
                    Log.i("API", "Login Success" + errorString + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return call;
    }

    public LiveData<User> getUser(String id){
        MutableLiveData<User> user = new MutableLiveData<>();
        service.getUser(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return user;
    }

    public Call<ResponseBody> createUser(User user) {
        Call<ResponseBody> call = service.createUser(user);

        service.createUser(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    String responseString = response.body().toString();
                    Log.i("API", "Insert Success" + responseString + " " + response.message());
                }
                else{
                    String responseString = response.errorBody().toString();
                    Log.i("API", "Insert Failed "+responseString+" "+response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Log.e("API", "Insert Failed");

            }
        });
        return call;
    }
}
