/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.content;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class OrderItemView extends LinearLayout {
    public OrderItemView(Context context ) {
        super( context );
        initialize( context );
    }

    public OrderItemView(Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        initialize( context );
    }

    public OrderItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr ) {
        super( context, attrs, defStyleAttr );
        initialize( context );
    }

    private void initialize( Context context ) {
        setOrientation( VERTICAL );
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState( Parcelable state ) {
        super.onRestoreInstanceState( state );
    }
}