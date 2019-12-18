/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.databinding.CartListItemBinding;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.viewmodels.CartItemViewModel;
import com.cti.lifego.viewmodels.CartViewModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final CartListItemBinding binding;

        ViewHolder(@NonNull CartListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(CartItem cartItem){
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CartListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_list_item,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItemViewModel viewModel = new CartItemViewModel();
        CartItem cartItem = cartItems.get(position);
        viewModel.setCartItem(cartItem);
        holder.binding.setCartItemView(viewModel);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (cartItems !=null){
            return cartItems.size();
        }return 0;
    }

    public void updateCartList(List<CartItem> cartItems){
        this.cartItems.clear();
        this.cartItems.addAll(cartItems);
        notifyDataSetChanged();
    }
}
