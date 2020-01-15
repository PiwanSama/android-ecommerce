/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.repositories;

import com.cti.lifego.api.NetworkService;
import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.models.Order;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutRepository {

    private static OrderRepository instance;
    private NetworkService service = RetrofitInstance.getRetrofitInstance().create(NetworkService.class);

    public static OrderRepository getInstance(){
        if (instance==null){
            instance = new OrderRepository();
        }
        return instance;
    }

    private void uploadFile(MultipartBody.Part file, RequestBody requestBody){
        Call<ResponseBody> call = service.uploadImage(file, requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void sendOrder(Order order){
        Call<ResponseBody> call = service.createOrder(order);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
