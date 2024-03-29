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
    private MutableLiveData<String> selectedStoreID = new MutableLiveData<String>();
    private LiveData<List<Store>> stores;
    private MutableLiveData<StoreViewState> storeViewState = new MutableLiveData<>();

    public enum StoreViewState{
        VIEW_ALL_STORES,
        VIEW_SINGLE_STORE
    }


    public void init(){
        storeViewState.setValue(StoreViewState.VIEW_ALL_STORES);
        if (stores!=null){
            return;
        }
        StoreRepository storeRepository = StoreRepository.getInstance();
        stores = storeRepository.listStores("2");
    }
    public MutableLiveData<StoreViewState> getViewState() {
        return storeViewState;
    }

    public void select(String id) {
        selectedStoreID.setValue(id);
        storeViewState.setValue(StoreViewState.VIEW_SINGLE_STORE);
    }

    public LiveData<List<Store>> listStores(Integer categoryID){
        return stores;
    }
}
