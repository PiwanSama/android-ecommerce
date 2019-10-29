package com.horlobyte.ctipharmacy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.horlobyte.ctipharmacy.R;

public class Browse extends AppCompatActivity {

    CardView click;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_category);
        click = findViewById(R.id.card_click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Browse.this, SingleProduct.class));
            }
        });
    }
}
