/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cti.lifego.BR;
import com.cti.lifego.models.Store;
import com.cti.lifego.repositories.StoreRepository;

import java.util.List;

public class StoreViewModel extends ViewModel {
    private LiveData<List<Store>> stores;
    private StoreRepository storeRepository;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private Boolean imageVisibility;
    private Store store;

    public void init(){
        if (stores!=null){
            return;
        }
        storeRepository = StoreRepository.getInstance();
        stores = storeRepository.listStores();
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public LiveData<List<Store>> getStores(){
        return stores;
    }

    public LiveData<Store> getStore(String storeID){
        MutableLiveData<Store> store = storeRepository.getStore(storeID);
        return store;
    }

    public Boolean getImageVisibility() {
        return imageVisibility;
    }

    public void setImageVisibility(Boolean imageVisibility) {
        this.imageVisibility = imageVisibility;
    }
}
