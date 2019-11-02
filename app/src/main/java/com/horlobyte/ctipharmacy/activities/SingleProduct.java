package com.horlobyte.ctipharmacy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.horlobyte.ctipharmacy.R;

public class SingleProduct extends AppCompatActivity {

    Button add, buy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        add = findViewById(R.id.add_to_cart);
        buy = findViewById(R.id.buy_now);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleProduct.this, Checkout.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "Item added to your cart", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
}
