/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.FloatingCardOrderBinding;
import com.cti.lifego.viewmodels.FloatingOrderViewModel;

public class FloatingCardOrderFragment extends Fragment {

    FloatingCardOrderBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater,R.layout.floating_card_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingOrderViewModel viewModel = new ViewModelProvider(this).get(FloatingOrderViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        NavController navController = Navigation.findNavController(view);

        view.setOnClickListener(v -> navController.navigate(R.id.checkout_graph));
    }
}
