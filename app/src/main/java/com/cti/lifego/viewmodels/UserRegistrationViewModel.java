/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.api.UserService;
import com.cti.lifego.models.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegistrationViewModel extends ViewModel {
    public MutableLiveData<String> givenName = new MutableLiveData<>();
    public MutableLiveData<String> surname = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> dob = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();
    public MutableLiveData<String> opt_in = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> nok_name = new MutableLiveData<>();
    public MutableLiveData<String> nok_phone = new MutableLiveData<>();
    public MutableLiveData<String> nok_relationship = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;

    public MutableLiveData<User> setUser(){
        if (userMutableLiveData == null){
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void postUser() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://core.ctiafrica.io/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService service = retrofit.create(UserService.class);

        Call<User> call = service.createUser(new User(givenName.getValue(),surname.getValue(),email.getValue(),phone.getValue(),dob.getValue(),gender.getValue(),opt_in.getValue(),password.getValue(),nok_name.getValue(),nok_phone.getValue(),nok_relationship.getValue()));

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
