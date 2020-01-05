/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cti.lifego.api.NetworkService;
import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.models.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRepository {

    private static StoreRepository instance;
    private NetworkService service = RetrofitInstance.getRetrofitInstance().create(NetworkService.class);

    public static StoreRepository getInstance(){
        if(instance == null){
            instance = new StoreRepository();
        }
        return instance;
    }

    public LiveData<List<Store>> listStores(String categoryID){
        final MutableLiveData<List<Store>> stores = new MutableLiveData<>();
        service.getStores(categoryID).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                stores.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {

            }
        });
        return stores;
    }
}
