package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.FragmentCheckoutPricingBinding;
import com.cti.lifego.viewmodels.CheckoutViewModel;

import static com.cti.lifego.viewmodels.CheckoutViewModel.CheckoutState.CHECKOUT_PAYMENT;

public class CheckoutPricing extends BaseFragment {

    private CheckoutViewModel viewModel;
    private NavController navController;
    private FragmentCheckoutPricingBinding binding;
    private String payment_option;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout_pricing, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        viewModel.checkout_state.observe(getViewLifecycleOwner(), checkoutState -> {

        });

        binding.makePayment.setOnClickListener(v -> {
            Toast.makeText(getContext(), payment_option, Toast.LENGTH_SHORT).show();
            viewModel.getCheckout_state().setValue(CHECKOUT_PAYMENT);
        });
    }
}
