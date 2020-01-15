/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cti.lifego.R;
import com.cti.lifego.adapters.OrderStepperAdapter;
import com.cti.lifego.databinding.FragmentOrderDetailsBinding;
import com.liefery.android.vertical_stepper_view.VerticalStepperView;

public class OrderDetailFragment extends BaseFragment{
    private Context mContext;
    private FragmentOrderDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.fragment_order_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderStepperAdapter adapter = new OrderStepperAdapter(mContext);
        VerticalStepperView stepperView = binding.verticalStepper;
        stepperView.setStepperAdapter(adapter);

    }
}
