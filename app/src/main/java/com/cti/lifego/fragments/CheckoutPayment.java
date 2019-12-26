package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.PaymentOptionsAdapter;
import com.cti.lifego.content.PaymentOptions;
import com.cti.lifego.models.PaymentOption;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckoutPayment extends Fragment {

    private RecyclerView recyclerView;
    private PaymentOptionsAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.checkout_payment, container, false);
        PaymentOptions paymentOptions = new PaymentOptions();
        ArrayList<PaymentOption> paymentArrayList = new ArrayList<>(Arrays.asList(paymentOptions.PAYMENT_OPTIONS));
        adapter = new PaymentOptionsAdapter(paymentArrayList);
        recyclerView = view.findViewById(R.id.payment_options_recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);
    }
}
