/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.repositories;

import android.graphics.Color;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.cti.lifego.api.MapsRetrofitInstance;
import com.cti.lifego.api.NetworkService;
import com.cti.lifego.models.MapsModels.DirectionResults;
import com.cti.lifego.models.MapsModels.Distance;
import com.cti.lifego.models.MapsModels.Duration;
import com.cti.lifego.models.MapsModels.Location;
import com.cti.lifego.models.MapsModels.Route;
import com.cti.lifego.models.MapsModels.Steps;
import com.cti.lifego.utils.RouteDecode;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsRepository {
    private static MapsRepository instance;
    private NetworkService service = MapsRetrofitInstance.getRetrofitInstance().create(NetworkService.class);
    private Steps steps;
    private Location location;
    private String polyline;
    private PolylineOptions rectLine;

    private MutableLiveData<PolylineOptions> mutableLiveDataOptions = new MutableLiveData<>();
    private MutableLiveData<Integer> durationLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> distanceLiveData = new MutableLiveData<>();

    public static MapsRepository getInstance() {
        if (instance == null) {
            instance = new MapsRepository();
        }
        return instance;
    }

    public MutableLiveData<PolylineOptions> getRoute(String requestAPI) {

        service.getDirection(requestAPI).enqueue(new Callback<DirectionResults>() {
            @Override
            public void onResponse(Call<DirectionResults> call, Response<DirectionResults> response) {

                DirectionResults directionResults = response.body();

                ArrayList<LatLng> routelist = new ArrayList<>();
                ArrayList<LatLng> decodelist;

                Route routeA = directionResults.getRoutes().get(0);

                if (routeA.getLegs().size() > 0) {
                    List<Steps> stepsList = routeA.getLegs().get(0).getSteps();

                    Duration duration = routeA.getLegs().get(0).getDuration();
                    durationLiveData.setValue(Integer.parseInt(duration.getValue()));

                    Distance distance = routeA.getLegs().get(0).getDistance();
                    distanceLiveData.setValue(Integer.parseInt(distance.getValue()));

                    for (int i = 0; i < stepsList.size(); i++) {
                        steps = stepsList.get(i);
                        location = steps.getStart_location();
                        routelist.add(new LatLng(location.getLat(), location.getLng()));
                        polyline = steps.getPolyline().getPoints();
                        decodelist = RouteDecode.decodePoly(polyline);
                        routelist.addAll(decodelist);
                        location = steps.getEnd_location();
                        routelist.add(new LatLng(location.getLat(), location.getLng()));
                    }
                }
                if (routelist.size() > 0) {
                    rectLine = new PolylineOptions().width(7).color(
                            Color.BLACK);
                    for (int i = 0; i < routelist.size(); i++) {
                        rectLine.add(routelist.get(i));
                    }
                }
                mutableLiveDataOptions.setValue(rectLine);
            }

            @Override
            public void onFailure(Call<DirectionResults> call, Throwable t) {
                Log.i("Fail", t.getMessage());
            }
        });
        return mutableLiveDataOptions;
    }

    public MutableLiveData<Integer> getDurationLiveData() {
        return durationLiveData;
    }

    public MutableLiveData<Integer> getDistanceLiveData() {
        return distanceLiveData;
    }
}

