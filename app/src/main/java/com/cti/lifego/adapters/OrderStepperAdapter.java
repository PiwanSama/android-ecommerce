/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cti.lifego.content.OrderItemView;
import com.liefery.android.vertical_stepper_view.VerticalStepperAdapter;

public class OrderStepperAdapter extends VerticalStepperAdapter {

    public OrderStepperAdapter(Context context){
        super(context);
    }

    @NonNull
    @Override
    public CharSequence getTitle(int position) {
        String title;
        switch (position){
            case 0:
                title = "Order placed";
                break;
            case 1:
                title = "Order accepted";
                break;
            case 2:
                title = "Rider dispatched";
                break;
            case 3:
                title = "Rider arrived";
                break;
            case 4:
                title = "Order delivered";
                break;
            default:
                return "Title";
        }
        return title;
    }

    @Nullable
    @Override
    public CharSequence getSummary(int position) {
        String title;
        switch (position){
            case 0:
                title = "The vendor has received your order";
                break;
            case 1:
                title = "The vendor is processing your order";
                break;
            case 2:
                title = "The rider is on his way to your location";
                break;
            case 3:
                title = "The rider has arrived at your location";
                break;
            case 4:
                title = "Your order is complete!";
                break;
            default:
                return "Title";
        }
        return title;
    }

    @Override
    public boolean isEditable(int position) {
        return false;
    }

    @NonNull
    @Override
    public View onCreateContentView(Context context, int position) {
        return new OrderItemView(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Void getItem(int position) {
        return null;
    }

}
