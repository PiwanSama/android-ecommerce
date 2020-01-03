package com.cti.lifego.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cti.lifego.R;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.ProductDetailBinding;
import com.cti.lifego.models.Product;
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailFragment extends BaseFragment {
    private ProductDetailBinding binding;
    private ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()){
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.no_internet, container, false);
        }
        else {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.no_internet, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.productShimmer.startShimmer();
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(productViewModel);

        productViewModel.getSelected().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        MutableLiveData<Product> productMutableLiveData = productViewModel.getProduct(s);
                        Product product = productMutableLiveData.getValue();
                        binding.setProduct(product);
                        binding.productShimmer.stopShimmer();
                        binding.productShimmer.setVisibility(View.GONE);
                    }
                });
        CircleImageView add =  binding.addToCart;
        add.setOnClickListener(v -> {
            add.setEnabled(false);
            add.setCircleBackgroundColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorAccent));
            addToCart(binding.getProduct());
        });
    }

    private void addToCart(Product product){
        //Add the product ID to shared preferences so we can fetch it from the remote database in the cart fragment
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        Set<String> productIDs = preferences.getStringSet(PreferenceKeys.shopping_cart_ids, new HashSet<String>());
        assert productIDs != null;
        productIDs.add(String.valueOf(product.getId()));
        editor.putStringSet(PreferenceKeys.shopping_cart_ids, productIDs);
        editor.commit();

        //Commit the updated quantity
        editor.putInt(String.valueOf(product.getId()), 1);
        editor.commit();


    }
}
