package com.cti.lifego.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.PaymentOptionsAdapter;
import com.cti.lifego.content.PaymentOptions;
import com.cti.lifego.models.PaymentOption;
import com.cti.lifego.viewmodels.CheckoutViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import static com.cti.lifego.viewmodels.CheckoutViewModel.CheckoutState.CHECKOUT_LOCATION;

public class CheckoutPayment extends BaseFragment {

    private Context mContext;
    private CheckoutViewModel viewModel;
    private NavController navController;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.checkout_pricing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        PaymentOptions paymentOptions = new PaymentOptions();
        ArrayList<PaymentOption> paymentArrayList = new ArrayList<>(Arrays.asList(paymentOptions.PAYMENT_OPTIONS));
        PaymentOptionsAdapter adapter = new PaymentOptionsAdapter(mContext, paymentArrayList);
        RecyclerView recyclerView = view.findViewById(R.id.payment_options_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);



        viewModel.getCheckout_state().observe(getViewLifecycleOwner(), checkoutState -> {
            if (checkoutState == CHECKOUT_LOCATION) {
                if (navController.getCurrentDestination().getId() == R.id.checkoutLocationFragment){
                    navController.navigate(R.id.action_checkoutLocationFragment_to_checkoutPricingFragment);
                }
            }
        });
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
