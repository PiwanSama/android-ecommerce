package com.horlobyte.ctipharmacy.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.horlobyte.ctipharmacy.R;
import com.horlobyte.ctipharmacy.activities.Browse;

public class HomeFragment extends Fragment {

    CardView cardView;
    Activity mActivity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mActivity = getActivity();
        cardView = view.findViewById(R.id.card1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, Browse.class));
            }
        });
        return view;
    }

}
