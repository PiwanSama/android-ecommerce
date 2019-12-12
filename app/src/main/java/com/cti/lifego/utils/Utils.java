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

    public static String getPhoneNumber(String str1, String str2){
        return str1.concat(str2);
    }

    public static String getQuantityString(int quantity){
        return (String.valueOf(quantity));
    }

    public static String convertIntToString(int value){
        return (String.valueOf(value));
    }

    public static String getOrderDate(int value){
        return ("Ordered on "+ String.valueOf(value));
    }

    public static String getDeliveryDate(int value){ return ("Delivered on "+ String.valueOf(value)); }

}
