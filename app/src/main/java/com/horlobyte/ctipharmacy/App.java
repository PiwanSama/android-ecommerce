package com.horlobyte.ctipharmacy;

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

    //Logs out user and redirects to login activity
    public static void logout() {
        //FirebaseAuth.getInstance().signOut();
    }

    public static Context getContext(){
        return mContext;
    }
}
