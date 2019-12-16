/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cti.lifego.BR;
import com.cti.lifego.models.Store;
import com.cti.lifego.repositories.StoreRepository;

import java.util.List;

public class StoreViewModel extends BaseObservable {
    private LiveData<List<Store>> stores;
    private MutableLiveData<Store> store;
    private StoreRepository storeRepository;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private Boolean imageVisibility;

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
        store = storeRepository.getStore(storeID);
        return store;
    }

    @Bindable
    public Boolean getImageVisibility() { return imageVisibility; }

    public void setImageVisibility(boolean imageVisibility){
        this.imageVisibility = imageVisibility;
        notifyPropertyChanged(BR.imageVisibility);
    }

    public RequestListener customRequestListener(){
        return new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };
    }
}
