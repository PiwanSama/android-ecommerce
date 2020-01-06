/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.cti.lifego.R;
import com.cti.lifego.api.MapsRetrofitInstance;
import com.cti.lifego.api.NetworkService;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CheckoutLocation extends BaseFragment implements GoogleMap.OnMyLocationButtonClickListener, OnMapReadyCallback{

    private FusedLocationProviderClient mfusedLocationProviderClient;
    private Location currentLocation;
    private GoogleMap mMap;
    private View mapView;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 2;
    private static final int PERMISSIONS_REQUEST_FROM_SETTINGS = 3;

    private NetworkService service = MapsRetrofitInstance.getRetrofitInstance().create(NetworkService.class);

    private List<LatLng> polyLineList;
    private Marker pickupPoint;
    private float v;
    private double lat,lng;
    private Handler handler;
    private LatLng startPosition, endPosition, currentPosition;
    private int index, next;
    private Button btnGo;
    private String destination;
    private PolylineOptions polylineOptions, blackPolyLineOptions;
    private Polyline greyPolyline, blackPolyline;

    Runnable drawPathRunnable = new Runnable() {
        @Override
        public void run() {
            if (index<polyLineList.size()-1){
                index++;
                next = index+1;
            }
            if (index<polyLineList.size()-1){
                startPosition = polyLineList.get(index);
                endPosition = polyLineList.get(next);
            }

            ValueAnimator animator = ValueAnimator.ofFloat(0,1);
            animator.setDuration(3000)
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    v = animator.getAnimatedFraction();
                    lng = v*endPosition.longitude +
                }
            });

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Places.initialize(getContext(), getResources().getString(R.string.MAPS_KEY));
        PlacesClient placesClient = Places.createClient(getContext());
        return inflater.inflate(R.layout.checkout_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView placeText = view.findViewById(R.id.selected_place);

        AutocompleteSupportFragment autoCompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.auto_complete_fragment);
        if (autoCompleteFragment !=null){
            autoCompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
            autoCompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                   placeText.setText(place.getName());
                    Log.i("LOC", "Place: " + place.getName() + ", " + place.getId());
                }

                @Override
                public void onError(Status status) {

                }
            });
        }
        else {
            //Place fragment returned null
            Log.i("pls", "Fragment was null");
        }

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();
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
        mMap.setOnMapLoadedCallback(this::mapInit);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        if (mapView!=null && mapView.findViewById(Integer.parseInt("1"))!=null){
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,30,200);
        }
    }

    private void mapInit() {
        Task<Location> task = mfusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location!=null){
                currentLocation = location;
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .title("You are here")
                        .position(latLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16.0f));
                mMap.addMarker(markerOptions);
            }
        });

        polyLineList = new ArrayList<>();
        //Get selected place here pls
    }

    private void getDirection(){
        currentPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        String requestAPI = null;
        try {
            requestAPI = "/json?"+
                    "mode=driving"+
                    "transit_routing_preference=less_driving"+
                    "origin="+currentPosition.latitude+","+
                    currentPosition.longitude+"&"+
                    "destination="+destination+
                    "key"+getResources().getString(R.string.MAPS_KEY);

            service.getDirection(requestAPI).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try{
                        assert response.body() != null;
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray jsonArray = jsonObject.getJSONArray("routes");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject route = jsonArray.getJSONObject(i);
                            JSONObject poly = route.getJSONObject("overview_polyline");
                            String polyline = poly.getString("points");
                            polyLineList = decodePolyline(polyline);
                        }

                    //Adjusting bounds
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (LatLng latLng : polyLineList){
                            builder.include(latLng);
                            LatLngBounds bounds = builder.build();
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);

                            polylineOptions = new PolylineOptions();
                            polylineOptions.color(Color.GRAY);
                            polylineOptions.width(5);
                            polylineOptions.startCap(new SquareCap());
                            polylineOptions.endCap(new SquareCap());
                            polylineOptions.jointType(JointType.ROUND);
                            polylineOptions.addAll(polyLineList);
                            greyPolyline = mMap.addPolyline(polylineOptions);

                            blackPolyLineOptions = new PolylineOptions();
                            blackPolyLineOptions.color(Color.GRAY);
                            blackPolyLineOptions.width(5);
                            blackPolyLineOptions.startCap(new SquareCap());
                            blackPolyLineOptions.endCap(new SquareCap());
                            blackPolyLineOptions.jointType(JointType.ROUND);
                            blackPolyLineOptions.addAll(polyLineList);
                            blackPolyline = mMap.addPolyline(blackPolyLineOptions);

                            mMap.addMarker(new MarkerOptions().position(polyLineList.get(polyLineList.size() - 1))
                                    .title("PickupLocation"));

                            ValueAnimator polyLineAnimator = ValueAnimator.ofInt(0,100);
                            polyLineAnimator.setDuration(2000);
                            polyLineAnimator.setInterpolator(new LinearInterpolator());
                            polyLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    List<LatLng> points = greyPolyline.getPoints();
                                    int percentage = (int) polyLineAnimator.getAnimatedValue();
                                    int size = points.size();
                                    int newPoints = (int) (size * percentage / 100);
                                    List<LatLng> p = points.subList(0, newPoints);
                                    blackPolyline.setPoints(p);
                                }
                            });
                            polyLineAnimator.start();

                            pickupPoint = mMap.addMarker(new MarkerOptions().position(currentPosition)
                            .flat(true));

                            handler = new Handler();
                            index = -1;
                            next = 1;
                            handler.post(drawPathRunnable, 3000);

                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
        }
        catch (Exception e){

        }
    }

    private List<LatLng> decodePolyline(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            poly.add(new LatLng((double) lat / 1E5, (double) lng / 1E5));
        }

        return poly;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
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
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                //User has denied the location permissions
                displayNeverAskAgainDialog();
            }
            else{
                //Do nothing because permission is already granted
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

}

