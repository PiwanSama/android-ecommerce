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
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.api.MapsRetrofitInstance;
import com.cti.lifego.api.NetworkService;
import com.cti.lifego.content.Constants;
import com.cti.lifego.databinding.FragmentCheckoutLocationBinding;
import com.cti.lifego.services.FetchAddressIntentService;
import com.cti.lifego.viewmodels.CheckoutViewModel;
import com.cti.lifego.viewmodels.LocationViewModel;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class CheckoutLocation extends BaseFragment implements GoogleMap.OnMyLocationButtonClickListener, OnMapReadyCallback{

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private GoogleMap mMap;
    private View mapView;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 2;
    private static final int PERMISSIONS_REQUEST_FROM_SETTINGS = 3;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 4;

    private Marker userLocationMarker, storeLocationMarker;
    private Marker destinationMarker;
    private Polyline routePolyline;

    private String addressOutput;
    LinearLayout search;
    private AddressReceiver resultReceiver;

    private FragmentCheckoutLocationBinding binding;
    private CheckoutViewModel viewModel;
    private LocationViewModel locationViewModel;
    private PlacesClient placesClient;
    private NavController navController;

    private NetworkService service = MapsRetrofitInstance.getRetrofitInstance().create(NetworkService.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        resultReceiver = new AddressReceiver(null);
        Places.initialize(Objects.requireNonNull(getContext()), getResources().getString(R.string.MAPS_KEY));
        placesClient = Places.createClient(getContext());

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout_location, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        search = view.findViewById(R.id.map_search_button);
        search.setOnClickListener(v -> setUpPlace());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        binding.navigateToPayment.setOnClickListener(v -> {
            viewModel.delivery_fee.setValue("2000/=");
        });
       // viewModel.checkout_state.setValue(CHECKOUT_LOCATION);

      //  getSelectedPlace();
    }

    private void setUpPlace() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("UG")
                .build(getContext());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
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
        mMap.setMinZoomPreference(14);

        if (mapView!=null && mapView.findViewById(Integer.parseInt("1"))!=null){
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,30,640);
        }
        
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Location location) -> {
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                currentLocation = location;
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                mMap.addMarker(options);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            }
        });
    }

    private void getDirection(Double destinationLat, Double destinationLong){
        String requestAPI = null;

        requestAPI = "json?"+
                "mode=driving&"+
                "origin="+currentLocation.getLatitude()+","+ currentLocation.getLongitude()+"&"+
                "destination="+destinationLat+","+ destinationLong+"&"+
                "key="+getResources().getString(R.string.MAPS_KEY);
        Log.i("API REQUEST", requestAPI);
        locationViewModel.getPolyLine(requestAPI).observe(getViewLifecycleOwner(), polylineOptions -> {
            if (routePolyline!=null){
                routePolyline.remove();
                routePolyline = mMap.addPolyline(polylineOptions);
            }
            else{ routePolyline = mMap.addPolyline(polylineOptions); }
        }
        );
    }

    private void getDistance() {
//        double distance = SphericalUtil.computeDistanceBetween(userLocationMarker.getPosition(), storeLocationMarker.getPosition());
        //Log.i("Distance", formatNumber(distance) + " apart.");
    }

    private String formatNumber(double distance) {
        String unit = "m";
        if (distance < 1) {
            distance *= 1000;
            unit = "mm";
        } else if (distance > 1000) {
            distance /= 1000;
            unit = "km";
        }

        return String.format("%4.3f%s", distance, unit);
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
                    .setNumUpdates(1);

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
            case AUTOCOMPLETE_REQUEST_CODE:
                if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        Place place = Autocomplete.getPlaceFromIntent(data);
                        getPlaceRoute(place);
                    } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                        // TODO: Handle the error.
                        Status status = Autocomplete.getStatusFromIntent(data);
                    } else if (resultCode == RESULT_CANCELED) {
                        // The user canceled the operation.
                    }
                }
        }
    }

    private void getPlaceRoute(Place place){
        LatLng placeLatLng = place.getLatLng();
        assert placeLatLng != null;
        getDirection(placeLatLng.latitude, placeLatLng.longitude);
        MarkerOptions options = new MarkerOptions().draggable(false).position(placeLatLng);
        if (destinationMarker!=null){
            destinationMarker.remove();
            destinationMarker = mMap.addMarker(options);
        }else{
            destinationMarker = mMap.addMarker(options);
        }
        CameraUpdate destinationLocation = CameraUpdateFactory.newLatLngZoom(placeLatLng, 8);
        mMap.animateCamera(destinationLocation);
        getDistance();
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

    protected void startIntentService() {
        Intent intent = new Intent(getContext(), FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, currentLocation);
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    class AddressReceiver extends ResultReceiver {
        public AddressReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultData == null) {
                return;
            }
            // Display the address string
            // or an error message sent from the intent networkService.
            addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            if (addressOutput == null) {
                addressOutput = "";
            }
            if (resultCode == Constants.SUCCESS_RESULT) {
                Toast.makeText(getContext(), getString(R.string.address_found), Toast.LENGTH_SHORT).show();
            }
            updateUI(addressOutput);
            // Show a toast message if an address was found.
        }

    }

    private void updateUI(String addressText){
        /*Objects.requireNonNull(getActivity()).runOnUiThread(() ->
                binding.selectedPlace.setText(addressText));*/

        binding.selectedPlace.setText(addressText);
    }
}

