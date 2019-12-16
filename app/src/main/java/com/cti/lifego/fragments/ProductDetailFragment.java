package com.cti.lifego.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.cti.lifego.R;
import com.cti.lifego.activities.MainActivity;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.ProductDetailBinding;
import com.cti.lifego.models.Product;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailFragment extends Fragment {
    private ProductDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.product_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Product product = binding.getProduct();
        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(product,1);
            }
        });
    }

    public void addToCart(Product product, int quantity){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> productIDs = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        assert productIDs != null;
        productIDs.add(String.valueOf(product.getId()));
        editor.putStringSet(PreferenceKeys.shopping_cart, productIDs);
        editor.commit();

        int currentQuantity = preferences.getInt(String.valueOf(product.getId()), 0);

        editor.putInt(String.valueOf(product.getId()), (currentQuantity + quantity));
        editor.commit();

        getShoppingCart();

        Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getView()), R.string.item_added, BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
    }

    public void getShoppingCart() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.getStringSet(PreferenceKeys.shopping_cart,new HashSet<>());
    }
}
