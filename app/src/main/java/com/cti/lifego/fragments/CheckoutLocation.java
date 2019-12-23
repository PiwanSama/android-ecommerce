/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cti.lifego.R;
import com.cti.lifego.activities.MainActivity;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CheckoutLocation extends Fragment implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    private FusedLocationProviderClient mfusedLocationProviderClient;
    private Location currentLocation;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 2;
    private static final int PERMISSIONS_REQUEST_FROM_SETTINGS = 3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.checkout_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getGpsStatus();
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission((getContext()), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            setUpMap();
        }
        else{
            getLocationPermission();
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapLoadedCallback(this::mapInit);
    }

    private void mapInit() {
        Task<Location> task = mfusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location!=null){
                currentLocation = location;
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.0f));
                mMap.addMarker(markerOptions);
            }
        });
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission((getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Location Permission not granted, start request
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                //Explain to the user why you need the permission
                displayNeverAskAgainDialog();
            }
            else {
                // No explanation needed, request the permission
                 requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        else {
            //Location permission already granted
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("Fragment", "Calling permission results");
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                //User has denied the location permissions
                Toast.makeText(getActivity(), "NO", Toast.LENGTH_SHORT).show();
                displayNeverAskAgainDialog();
            }
            else{
                //Do nothing because permission is already granted
                Toast.makeText(getActivity(), "YAY!!", Toast.LENGTH_SHORT).show();
                setUpMap();
            }
        }
    }

    private void getGpsStatus() {

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gps_enabled){
            LocationRequest mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000)
                    .setNumUpdates(2);

            final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest)
                    .setAlwaysShow(true);

            SettingsClient client = LocationServices.getSettingsClient(getActivity());
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

            task.addOnSuccessListener(locationSettingsResponse -> { });

            task.addOnFailureListener(e -> {
                if (e instanceof ResolvableApiException){
                    try {
                        //Show the dialog by calling startResolutionFroResult() and check the result onActivityResult
                        ResolvableApiException exception = (ResolvableApiException) e;
                        exception.startResolutionForResult(getActivity(), PERMISSIONS_REQUEST_ENABLE_GPS);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_ENABLE_GPS:
                if (resultCode == RESULT_OK){
                    //Do nothing because GPS is enabled
                }
                else {
                    //Repeat request for GPS enable
                    getGpsStatus();
                }
                break;
            case PERMISSIONS_REQUEST_FROM_SETTINGS:
                if (resultCode==RESULT_CANCELED){
                    if (ContextCompat.checkSelfPermission((getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivity(), "Location granted", Toast.LENGTH_SHORT).show();
                        setUpMap();
                    }
                    else {
                        //Repeat request for GPS enable
                        displayNeverAskAgainDialog();
                    }
                }
            }
    }

    private void displayNeverAskAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("We need to access your location. Please grant the permission in your settings screen.");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, PERMISSIONS_REQUEST_FROM_SETTINGS);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}
