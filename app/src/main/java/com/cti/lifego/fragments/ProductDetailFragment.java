package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.cti.lifego.R;
import com.cti.lifego.utils.StickyBottomBehavior;

public class ProductDetailFragment extends Fragment {

    MaterialButton add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.product_detail, container, false);
        add = view.findViewById(R.id.add_to_cart);
        ((CoordinatorLayout.LayoutParams) add.getLayoutParams()).setBehavior(new StickyBottomBehavior(R.id.anchor, getResources().getDimensionPixelOffset(R.dimen.margins)));
        return view;
    }
}
