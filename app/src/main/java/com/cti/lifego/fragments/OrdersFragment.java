package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cti.lifego.R;
import com.cti.lifego.utils.NetworkUtil;

import java.util.Objects;

public class OrdersFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected()){
            return inflater.inflate(R.layout.no_internet, container, false);
        }
        return inflater.inflate(R.layout.orders_fragment, container, false);
    }

    private boolean isConnected(){
        return NetworkUtil.getConnectivityString(Objects.requireNonNull(getContext()));
    }
}
