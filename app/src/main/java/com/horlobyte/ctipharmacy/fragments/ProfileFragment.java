package com.horlobyte.ctipharmacy.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.horlobyte.ctipharmacy.R;

public class ProfileFragment extends Fragment {

    Activity mActivity;
    ListView profileOptionsList;
    String [] profileOptionsArray = {"Address", "Payment Options", "Account", "Help", "About"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        mActivity=getActivity();
        profileOptionsList = view.findViewById(R.id.profile_options);
        ArrayAdapter adapter = new ArrayAdapter <String> (mActivity,R.layout.list_view_item,profileOptionsArray);
        profileOptionsList.setAdapter(adapter);
        return view;
    }
}

