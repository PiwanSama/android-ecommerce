package com.horlobyte.ctipharmacy.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.horlobyte.ctipharmacy.R;
import com.horlobyte.ctipharmacy.fragments.HomeFragment;
import com.horlobyte.ctipharmacy.fragments.OrdersFragment;
import com.horlobyte.ctipharmacy.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = getSupportActionBar();
        toolbar.setElevation(0);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new HomeFragment());
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);

        //Associate searchable configuration with searchview
        //SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = (SearchView)menu.findItem(R.id.product_search).getActionView();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home_item:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.orders_item:
                    fragment = new OrdersFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.profile_item:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
}
