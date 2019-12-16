/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.CheckoutLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class CheckoutLocation extends Fragment implements OnMapReadyCallback {

    CheckoutLocationBinding binding;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private Location currentLocation;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.checkout_location, container, false);
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (!Places.isInitialized()){
            Places.initialize(getContext(), "AIzaSyANbNmQEpFm7QdQ0h9qblys2Aip0o7kUBE");
        }

        PlacesClient placesClient = Places.createClient(getContext());
        fetchLocation();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.location_map);
        mapFragment.getMapAsync(this);
    }

    private void fetchLocation() {
        Task<Location> task = mfusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location!=null){
                currentLocation = location;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(() -> {
            mMap = googleMap;
            mapInit();
        });
    }

    private void mapInit() {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.0f));
        mMap.addMarker(markerOptions);
    }

}
