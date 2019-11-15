package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.google.android.material.button.MaterialButton;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class CheckoutPaymentFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.checkout_payment, container, false);
        StateProgressBar progressBar = view.findViewById(R.id.state_progress_bar);
        progressBar.setStateDescriptionData(getResources().getStringArray(R.array.progress_statebar_array));

        MaterialButton button = view.findViewById(R.id.make_payment_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_checkout_pricing_fragment_to_checkout_success_fragment);
            }
        });

        return view;
    }
}
