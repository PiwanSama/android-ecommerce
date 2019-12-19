package com.cti.lifego.fragments;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cti.lifego.R;
import com.cti.lifego.utils.NetworkChangeReceiver;

import java.util.Objects;

public class OrdersFragment extends Fragment {

    private BroadcastReceiver networkChangeReceiver = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkChangeReceiver = new NetworkChangeReceiver();
        return inflater.inflate(R.layout.orders_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getContext()).registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(getContext()).unregisterReceiver(networkChangeReceiver);
    }
}
