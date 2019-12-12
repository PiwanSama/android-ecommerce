/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.cti.lifego.BR;
import com.cti.lifego.models.Store;

public class StoreViewModel extends BaseObservable {
    private Store store;

    @Bindable
    public Store getStore(){
        return store;
    }

    public void setStore(Store store){
        this.store = store;
        notifyPropertyChanged(BR.store);
    }
}
