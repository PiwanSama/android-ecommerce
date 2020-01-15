/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.repositories.MapsRepository;
import com.google.android.gms.maps.model.PolylineOptions;

public class LocationViewModel extends ViewModel {
    private MutableLiveData<Integer> distance = new MutableLiveData<>();
    private MutableLiveData<Integer> duration = new MutableLiveData<>();
    private MapsRepository mapsRepository;

    public void init(){
        mapsRepository = MapsRepository.getInstance();
    }

    public MutableLiveData<PolylineOptions> getPolyLine(String requestAPI){
        Log.i("REPO", "Getting route....");
        return mapsRepository.getRoute(requestAPI);
    }

    public MutableLiveData<Integer> getDistance() {
        return mapsRepository.distanceLiveData;
    }

    public MutableLiveData<Integer> getDuration() {
        return mapsRepository.durationLiveData;
    }
}
