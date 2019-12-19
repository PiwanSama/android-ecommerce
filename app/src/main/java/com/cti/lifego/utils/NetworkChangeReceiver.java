/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    String text;
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = NetworkUtil.getConnectivityStatusString(context);
        if(!status) {
            text = "No Internet Connection";
        }
        Toast.makeText(context, text , Toast.LENGTH_LONG).show();
    }
}