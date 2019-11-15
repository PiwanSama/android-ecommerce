package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.google.android.material.button.MaterialButton;

public class CartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.cart_fragment, container, false);

        MaterialButton checkout = view.findViewById(R.id.checkout_button);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_checkout_summary_fragment);
            }
        });
        return view;
    }
}
