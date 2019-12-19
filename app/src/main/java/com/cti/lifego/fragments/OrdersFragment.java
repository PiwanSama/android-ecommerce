package com.cti.lifego.fragments;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cti.lifego.R;
import com.cti.lifego.activities.MainActivity;
import com.cti.lifego.utils.NetworkChangeReceiver;
import com.cti.lifego.utils.NetworkUtil;

public class OrdersFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetworkUtil.getConnectivityStatusString())
        return inflater.inflate(R.layout.orders_fragment, container, false);
    }
}
