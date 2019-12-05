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

public class CheckoutSummaryFragment extends Fragment {

    private StateProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.checkout_summary, container, false);

        progressBar = view.findViewById(R.id.state_progress_bar);
        progressBar.setStateDescriptionData(getResources().getStringArray(R.array.progress_statebar_array));
        progressBar.setStateDescriptionTypeface("fonts/quicksand_medium.ttf");

        MaterialButton button = view.findViewById(R.id.summary_proceed_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_checkout_summary_fragment_to_checkout_Location);
            }
        });
        return view;
    }
}
