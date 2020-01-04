package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.databinding.CartFragmentBinding;
import com.cti.lifego.intefaces.ICartFragment;
import com.cti.lifego.models.Cart;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.models.Product;
import com.cti.lifego.utils.CartHelper;
import com.cti.lifego.viewmodels.CartViewModel;
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.List;

public class CartFragment extends BaseFragment implements ICartFragment {

    private CartFragmentBinding binding;
    private CartViewModel cartViewModel;
    private ProductViewModel productViewModel;
    private List<CartItem> cartItems;
    private RecyclerView cartRecyclerView;
    private Cart cart = CartHelper.getCart();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.cart_fragment, container, false);

        cartRecyclerView = binding.cartRecyclerView;
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void updateQuantity(Product product, int quantity) {

    }

    @Override
    public void deleteItem(CartItem cartItem) {
    }

    @Override
    public void emptyCart() {

    }

    @Override
    public void checkout() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showBar(){
        binding.contentLoading.show();
    }

    private void cartEmpty(){
        binding.contentLoading.hide();
    }

    private void showCart(){
        binding.contentLoading.hide();
        binding.populatedCartView.setVisibility(View.VISIBLE);
    }

    //TODO - Selected Store

}

