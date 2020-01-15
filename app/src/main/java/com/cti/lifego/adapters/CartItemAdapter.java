/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.databinding.CartListItemBinding;
import com.cti.lifego.models.CartItem;
import com.cti.lifego.viewmodels.CartItemViewModel;

import java.util.ArrayList;
import java.util.List;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.BindingHolder>{

    private static final String TAG = "CartItemAdapter";

    private List<CartItem> mCartItems = new ArrayList<>();
    private Context mContext;

    public CartItemAdapter(Context context, List<CartItem> cartItems) {
        mCartItems = cartItems;
        mContext = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.cart_list_item, parent, false);
        Log.i("BIND", "Set Item");
        return new CartItemAdapter.BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        CartItem cartItem = mCartItems.get(position);
        CartItemViewModel viewModel = new CartItemViewModel();
        viewModel.setCartItem(cartItem);
        holder.binding.setCartView(viewModel);
        holder.binding.executePendingBindings();
        Log.i("ITEM", cartItem.getProduct().getName());
    }

    public void updateCartItems(List<CartItem> cartItems){
        mCartItems.clear();
        mCartItems.addAll(cartItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }


    public class BindingHolder extends RecyclerView.ViewHolder{

        CartListItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}


