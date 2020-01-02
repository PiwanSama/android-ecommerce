/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.Store;
import com.cti.lifego.repositories.StoreRepository;

import java.util.List;

public class StoreViewModel extends ViewModel {
    private MutableLiveData<Store> selectedStore = new MutableLiveData<Store>();
    private LiveData<List<Store>> stores;
    private StoreRepository storeRepository;

    public void init(){
        if (stores!=null){
            return;
        }
        storeRepository = StoreRepository.getInstance();
        stores = storeRepository.listStores();
    }

    public LiveData<List<Store>> getStores(){
        return stores;
    }

    public LiveData<Store> getStore(String storeID){
        selectedStore = storeRepository.getStore(storeID);
        return selectedStore;
    }

    public LiveData<Store> getSelected() {
        return selectedStore;
    }
}
