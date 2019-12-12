/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.databinding.CartListItemBinding;
import com.cti.lifego.models.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Product> cartList;

    public CartAdapter(List<Product> cartList){
        this.cartList = cartList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final CartListItemBinding binding;

        ViewHolder(@NonNull CartListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(Product product){
            binding.setProduct(product);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartListItemBinding binding = CartListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = cartList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        if (cartList!=null){
            return cartList.size();
        }return 0;
    }
}
