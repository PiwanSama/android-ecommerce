package com.horlobyte.ctipharmacy.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.transition.Slide;
import androidx.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.horlobyte.ctipharmacy.R;
import com.horlobyte.ctipharmacy.activities.Browse;

public class HomeFragment extends Fragment {

    Activity mActivity;
    ConstraintLayout card;
    Intent i;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mActivity = getActivity();
        card = view.findViewById(R.id.cardy);
        i = new Intent(mActivity, Browse.class);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(i);
            }
        });
        return view;
    }
}
