package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cti.lifego.R;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class CheckoutSuccess extends Fragment {

    StateProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.checkout_success, container, false);
        progressBar = view.findViewById(R.id.state_progress_bar);
        progressBar.setStateDescriptionData(getResources().getStringArray(R.array.progress_statebar_array));
        return view;
    }
}
