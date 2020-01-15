/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.databinding.StoreListItemBinding;
import com.cti.lifego.models.Store;

import java.util.List;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder>{

    private List<Store> stores;
    private StoreClickListener listener;
    private Context context;

    public StoresAdapter(Context context, List<Store> stores, StoreClickListener listener){
        this.context = context;
        this.stores = stores;
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private StoreListItemBinding binding;

        ViewHolder(@NonNull StoreListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Store store, StoreClickListener listener){
            binding.setStore(store);
            itemView.setOnClickListener(v -> {
                listener.getStoreId(store);
            });
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public StoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoreListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.store_list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Store store = stores.get(position);
        holder.bind(store, listener);
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    public interface StoreClickListener {
        void getStoreId(Store store);
    }
}
