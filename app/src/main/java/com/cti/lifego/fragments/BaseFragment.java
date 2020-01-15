/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cti.lifego.utils.NetworkUtil;

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
