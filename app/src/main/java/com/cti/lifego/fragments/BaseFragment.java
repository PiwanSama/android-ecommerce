/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cti.lifego.R;
import com.cti.lifego.utils.NetworkUtil;
import com.wessam.library.NoInternetLayout;

public class BaseFragment extends Fragment {

    private Context mContext;

    boolean isNetworkConnected(){
        mContext = getContext();
        return NetworkUtil.getConnectivityString(mContext);
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
