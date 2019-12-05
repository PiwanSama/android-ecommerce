package com.cti.lifego;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class App extends Application {

    private final String TAG = this.getClass().getSimpleName();
    private static Context mContext;
    private Activity mActivity;
    Boolean mLocationPermissionGranted;
    public int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

    }



    public static Context getContext(){
        return mContext;
    }
}
