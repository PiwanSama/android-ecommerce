/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.databinding.CartListItemBinding;
import com.cti.lifego.models.Cart;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.utils.CartHelper;

import java.util.Collections;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private CartListItemBinding binding;
    private Cart cart = CartHelper.getCart();
    private List<CartItem> cartItems = Collections.emptyList();

    public CartAdapter(Context context)
    {
        this.context = context;
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        private final CartListItemBinding itemBinding;

        CartViewHolder(@NonNull CartListItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        void bind(CartItem cartItem)
        {
            binding.setCartItem(cartItem);
            binding.totalItemCost.setText(String.valueOf(cart.getCost(cartItem.getProduct())));
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_list_item,parent,false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int position) {
        CartItem cartItem = cartItems.get(position);
        cartViewHolder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateCartItems(List<CartItem> cartItems){
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }
}
