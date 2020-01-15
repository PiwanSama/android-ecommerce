/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.repositories.MapsRepository;
import com.google.android.gms.maps.model.PolylineOptions;

public class LocationViewModel extends ViewModel {
    private MapsRepository mapsRepository;

    public void init(){
        mapsRepository = MapsRepository.getInstance();
    }

    public MutableLiveData<PolylineOptions> getPolyLine(String requestAPI){
        return mapsRepository.getRoute(requestAPI);
    }

    public MutableLiveData<Integer> getTotalDistance() {
        return mapsRepository.getDistanceLiveData();
    }

    public MutableLiveData<Integer> getTotalDuration() {
        return mapsRepository.getDurationLiveData();
    }
}
