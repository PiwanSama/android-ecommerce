package com.cti.lifego.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cti.lifego.R;
import com.cti.lifego.adapters.UserProfileAdapter;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        Activity mActivity = getActivity();
        Resources res = getResources();
        String[] profileOptionsArray = res.getStringArray(R.array.user_profile_array);
        ListView profileOptionsList = view.findViewById(R.id.profile_options);
        profileOptionsList.setAdapter(new UserProfileAdapter(mActivity, profileOptionsArray));
        return view;
    }
}

