package com.cti.lifego.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.CartItemAdapter;
import com.cti.lifego.databinding.CartFragmentBinding;
import com.cti.lifego.intefaces.Saleable;
import com.cti.lifego.models.Cart;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.models.Product;
import com.cti.lifego.utils.CartHelper;
import com.cti.lifego.viewmodels.CartViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartFragment extends BaseFragment{

    private CartFragmentBinding binding;
    private CartViewModel cartViewModel;
    private RecyclerView cartRecyclerView;
    private CartItemAdapter adapter;
    private Cart cart;
    private TextView product_qty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.cart_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartRecyclerView = binding.cartRecyclerView;
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cart = CartHelper.getCart();
        binding.setCartView(cartViewModel);
        cartViewModel.setCartList(getCartItems(cart));
    }


    private List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        Log.d("Here", "Current shopping cart: " + cart);

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();

        for (Map.Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }
        //Log.d("Here", "Cart item list: " + cartItems);
        return cartItems;
    }
}

