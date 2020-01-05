package com.cti.lifego;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private final String TAG = this.getClass().getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
