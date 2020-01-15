/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.content;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cti.lifego.R;
import com.cti.lifego.adapters.CartItemAdapter;
import com.cti.lifego.models.CartItem;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BindingAdapters {
    @BindingAdapter({"android:errorText"})
    public static void setErrorMessage(TextInputLayout view, String errorMessage){
        view.setError(errorMessage);
    }

    @BindingAdapter("android:imageUrl")
    public static void setImage(ImageView view, int imageUrl){

        Context context = view.getContext();

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder);

        Glide.with (context)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }

    @BindingAdapter("cartItems")
    public static void setCartItems(RecyclerView view, List<CartItem> cartItems){
        if(cartItems == null){
            return;
        }
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if(layoutManager == null){
            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        CartItemAdapter adapter = (CartItemAdapter) view.getAdapter();
        if(adapter == null){
            adapter = new CartItemAdapter(view.getContext(), cartItems);
            view.setAdapter(adapter);
        }
        else{
            adapter.updateCartItems(cartItems);
        }
    }
}
