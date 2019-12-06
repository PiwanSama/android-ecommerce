package com.cti.lifego.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cti.lifego.App;

public class Utils {
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String getPhoneNumber(int countryCode, int phoneNumber){
        String str1 = Integer.toString(countryCode);
        String str2 = Integer.toString(phoneNumber);
        return str1.concat(str2);
    }
}
