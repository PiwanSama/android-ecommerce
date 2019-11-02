package com.horlobyte.ctipharmacy.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.horlobyte.ctipharmacy.R;

import static androidx.core.app.ActivityOptionsCompat.*;

public class Browse extends AppCompatActivity {

    CardView click;
    Intent i;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_category);
        click = findViewById(R.id.card_click);
        i = new Intent(Browse.this, SingleProduct.class);
        final View imageView = findViewById(R.id.m_image_1);
        final View text= findViewById(R.id.m_name_1);

        getWindow().getSharedElementEnterTransition().setDuration(2000);
        getWindow().getSharedElementEnterTransition().setDuration(2000).setInterpolator(new DecelerateInterpolator());


        click.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat transitionActivityOptions = makeSceneTransitionAnimation(Browse.this, imageView, "product_image_trans");
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });
    }

}
