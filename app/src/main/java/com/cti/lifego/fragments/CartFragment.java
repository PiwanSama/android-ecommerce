package com.cti.lifego.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.cti.lifego.R;
import com.cti.lifego.activities.MainActivity;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.CartFragmentBinding;
import com.cti.lifego.intefaces.ICartFragment;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.models.Product;
import com.cti.lifego.viewmodels.CartViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class CartFragment extends Fragment implements ICartFragment {

    private CartFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.cart_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void updateQuantity(Product product, int quantity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();

        int currentQuantity = preferences.getInt(String.valueOf(product.getId()),0);
        editor.putInt(String.valueOf(product.getId()), (currentQuantity + quantity));
        editor.commit();

        getShoppingCart();
    }

    @Override
    public void getShoppingCartList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> ids = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<>());

       //Products products = new Products();
        List<CartItem> cartItems = new ArrayList<>();
        for(String id: ids){
            int quantity = preferences.getInt(id,0);
           // cartItems.add(new CartItem());
        }
        CartViewModel cartViewModel = new CartViewModel();
        cartViewModel.setCartList(cartItems);
        binding.setCartView(cartViewModel);
    }

    public void getShoppingCart() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.getStringSet(PreferenceKeys.shopping_cart,new HashSet<>());
    }
}

