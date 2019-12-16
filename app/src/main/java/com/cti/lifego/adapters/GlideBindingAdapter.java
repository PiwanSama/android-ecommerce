/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;

public class GlideBindingAdapter {

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int imageURL){
        Context context = imageView.getContext();

        Glide.with(context)
                .load(imageURL)
                .into(imageView);
    }

    @BindingAdapter({"requestListener", "imageResource"})
    public static void bindRequestListener(ImageView imageView, RequestListener requestListener, int imageResource){
        Context context = imageView.getContext();

        Glide.with(context)
                .addDefaultRequestListener(requestListener)
                .load(imageResource)
                .into(imageView);
    }
}
