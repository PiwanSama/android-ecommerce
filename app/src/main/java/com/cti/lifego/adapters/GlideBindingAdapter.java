/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideBindingAdapter {

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView imageView, int imageURL){
        Context context = imageView.getContext();

        Glide.with(context)
                .load(imageURL)
                .into(imageView);
    }
}
