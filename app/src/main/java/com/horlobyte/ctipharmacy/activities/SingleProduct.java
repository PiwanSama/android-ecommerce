package com.horlobyte.ctipharmacy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.horlobyte.ctipharmacy.R;
import com.horlobyte.ctipharmacy.utils.StickyBottomBehavior;

public class SingleProduct extends AppCompatActivity {

    MaterialButton add;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add = findViewById(R.id.add_to_cart);

        ((CoordinatorLayout.LayoutParams) add.getLayoutParams()).setBehavior(new StickyBottomBehavior(R.id.anchor, getResources().getDimensionPixelOffset(R.dimen.margins)));
    }
}
