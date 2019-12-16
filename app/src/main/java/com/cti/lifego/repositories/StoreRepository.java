/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.api.StoresInterface;
import com.cti.lifego.models.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRepository {

    private static StoreRepository instance;
    private StoresInterface service = RetrofitInstance.getRetrofitInstance().create(StoresInterface.class);

    public static StoreRepository getInstance(){
        if(instance == null){
            instance = new StoreRepository();
        }
        return instance;
    }

    public LiveData<List<Store>> listStores(){
        final MutableLiveData<List<Store>> stores = new MutableLiveData<>();
        service.getStores().enqueue(new Callback<List<Store>>() {
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

    public MutableLiveData<Store> getStore(String storeId){
        final MutableLiveData<Store> store = new MutableLiveData<>();
        service.getStore(storeId).enqueue(new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> response) {
                store.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {

            }
        });
        return store;
    }
}
