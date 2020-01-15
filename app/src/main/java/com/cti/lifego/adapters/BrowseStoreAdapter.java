/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.databinding.ProductListItemBinding;
import com.cti.lifego.models.Product;

import java.util.List;

public class BrowseStoreAdapter extends RecyclerView.Adapter<BrowseStoreAdapter.ViewHolder>{

    private List<Product> products;
    private ProductClickListener listener;

    public BrowseStoreAdapter(List<Product> products, ProductClickListener listener){
        this.products = products;
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ProductListItemBinding binding;

        ViewHolder(@NonNull ProductListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Product product, ProductClickListener listener){
            binding.setProduct(product);
            binding.addToCart.setOnClickListener(v -> listener.getProductId(product));
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public BrowseStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface ProductClickListener {
        void getProductId(Product Product);
    }
}
