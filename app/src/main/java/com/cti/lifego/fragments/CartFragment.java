package com.cti.lifego.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cti.lifego.R;
import com.cti.lifego.adapters.CartAdapter;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.CartFragmentBinding;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.viewmodels.CartViewModel;
import com.cti.lifego.viewmodels.UserRegistrationViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartFragment extends Fragment {

    private CartFragmentBinding binding;
    private CartAdapter adapter;
    private List<CartItem> cartItems;
    CartViewModel cartViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.cart_fragment, container, false);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        binding.setCartView(cartViewModel);
        getShoppingCartList();
        return binding.getRoot();
    }

    private void getShoppingCartList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> ids = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<>());
        //Products products = new Products();
        cartItems = new ArrayList<>();
        if (ids!=null) {
            for (String id : ids) {
                int quantity = preferences.getInt(id,0);
                // cartItems.add(new CartItem());
            }
        }

        CartViewModel cartViewModel = new CartViewModel();
        cartViewModel.setCartList(cartItems);
        binding.setCartView(cartViewModel);
    }
}

