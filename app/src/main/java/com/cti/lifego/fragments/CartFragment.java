package com.cti.lifego.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartFragment extends BaseFragment{

    private CartFragmentBinding binding;
    private CartViewModel cartViewModel;
    private ProductViewModel productViewModel;
    private List<CartItem> cartItems;
    private RecyclerView cartRecyclerView;
    private CartItemAdapter adapter;
    private Cart cart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.cart_fragment, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cart = CartHelper.getCart();

        cartRecyclerView = binding.cartRecyclerView;
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        adapter = new CartItemAdapter(getContext(), cartItems);
        adapter.updateCartItems(getCartItems(cart));
    }

    private List<CartItem> getCartItems(Cart cart){
        cartItems = new ArrayList<CartItem>();

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();
        for (Map.Entry<Saleable, Integer> entry : itemMap.entrySet()){
            CartItem cartItem= new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        Log.d("CART", "Cart item list: " + cartItems);
        return cartItems;
    }

}

