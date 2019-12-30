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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.CartAdapter;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.CartFragmentBinding;
import com.cti.lifego.intefaces.ICartFragment;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.models.Product;
import com.cti.lifego.viewmodels.CartViewModel;
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartFragment extends Fragment implements ICartFragment {

    private CartFragmentBinding binding;
    private CartAdapter cartAdapter;
    private CartViewModel cartViewModel;
    ProductViewModel productViewModel;
    List<CartItem> cartItems;
    RecyclerView cartRecyclerView;

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
        getShoppingCartList();
        if (cartItems!=null){
            cartAdapter = new CartAdapter(cartItems);
            cartRecyclerView.setAdapter(cartAdapter);
        }
        else {
            Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void getShoppingCartList() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> ids = preferences.getStringSet(PreferenceKeys.shopping_cart_ids, new HashSet<>());

        //In this case I will populate the cartItemsList with Cart Items created using the Product IDS stores
        cartItems = new ArrayList<>();
        if (ids!=null) {
            for (String id : ids) {
                //You want to create a new cart item from that product id.
                cartItems.add(new CartItem(productViewModel.getProduct(id)));
            }
        }

        cartViewModel.setCartList(cartItems);
        binding.setCartView(cartViewModel);
    }

    public void updateCartItems(){
        getShoppingCartList();
    }

    @Override
    public void getQuantity(int quantity) {

    }

    @Override
    public void setQuantity(int quantity) {

    }

    @Override
    public void updateQuantity(CartItem cartItem) {

    }

    @Override
    public void setCartVisibility(boolean cartVisibility) {

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

}

