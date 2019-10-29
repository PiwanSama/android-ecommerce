package com.horlobyte.ctipharmacy.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.horlobyte.ctipharmacy.R;
import com.horlobyte.ctipharmacy.adapters.UserProfileAdapter;

public class ProfileFragment extends Fragment {

    Activity mActivity;
    ListView profileOptionsList;
    String [] profileOptionsArray = {"Address", "Payment Options", "Account", "Help", "About"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        mActivity = getActivity();
        profileOptionsList = view.findViewById(R.id.profile_options);
        profileOptionsList.setAdapter(new UserProfileAdapter(mActivity, profileOptionsArray));
        return view;
    }
}

