package com.horlobyte.ctipharmacy.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.horlobyte.ctipharmacy.R;

public class Checkout extends AppCompatActivity {

    Button add, buy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_summary);
    }
}
