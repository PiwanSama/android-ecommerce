package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.CartItemAdapter;
import com.cti.lifego.databinding.FragmentCheckoutSummaryBinding;
import com.cti.lifego.models.Cart;
import com.cti.lifego.utils.CartHelper;
import com.cti.lifego.viewmodels.CheckoutViewModel;

import java.util.ArrayList;

import static com.cti.lifego.viewmodels.CheckoutViewModel.CheckoutState.CHECKOUT_PAYMENT;

public class CheckoutSummary extends BaseFragment {

    FragmentCheckoutSummaryBinding binding;
    private CheckoutViewModel viewModel;
    private NavController navController;
    private Cart cart;
    private ArrayList cartList;
    private CartItemAdapter cartAdapter;
    private RecyclerView summaryCart;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout_summary, container, false);
        viewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return inflater.inflate(R.layout.fragment_checkout_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.navigateToPayment.setOnClickListener(v ->
                viewModel.checkout_state.setValue(CHECKOUT_PAYMENT));

        viewModel.getCheckout_state().observe(getViewLifecycleOwner(), checkoutState -> {
            if (checkoutState == CHECKOUT_PAYMENT) {
                if (navController.getCurrentDestination().getId() == R.id.checkoutLocationFragment) {
                    navController.navigate(R.id.action_checkoutSummaryFragment_to_checkoutPricingFragment);
                }
            }
        });

        summaryCart = binding.orderItemsSummaryRv;

        cart = CartHelper.getCart();
        cartList = new ArrayList();
        cartAdapter = new CartItemAdapter(getContext(), cartList);
    }
}
