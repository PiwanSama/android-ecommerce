package com.cti.lifego.fragments;

import android.os.Bundle;
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
import com.cti.lifego.databinding.ProductDetailBinding;
import com.cti.lifego.models.Cart;
import com.cti.lifego.models.Product;
import com.cti.lifego.utils.CartHelper;
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailFragment extends BaseFragment {
    private ProductDetailBinding binding;
    private ProductViewModel productViewModel;
    private Cart cart = CartHelper.getCart();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()){
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.no_internet, container, false);
        }
        else {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.product_detail, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // navHost.getChildFragmentManager().findFragmentById(R.id.)

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

        Product product = binding.getProduct();
        CircleImageView add =  binding.addToCart;
        add.setOnClickListener(v -> {
            add.setEnabled(false);
            add.setCircleBackgroundColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorAccent));
            //Add item to cart object
            cart.add(product, 1);
        });
    }
}
