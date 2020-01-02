package com.cti.lifego.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import com.cti.lifego.R;
import com.google.android.material.card.MaterialCardView;

public class BrowseStoreFragment extends Fragment {

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.browse_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onClick(){
        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
              //  .addSharedElement(imageView, "header_image")
               // .addSharedElement(titleView, "header_title")
                .build();
       // Navigation.findNavController(view).navigate(R.id.details,
             //   null, // Bundle of args
              //  null, // NavOptions
              //  extras);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }
}
