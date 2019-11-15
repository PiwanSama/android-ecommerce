package com.cti.lifego.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cti.lifego.R;
import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setUpNavigation();
    }

    private void setUpNavigation() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Set<Integer> topLevelDest = new HashSet<>();
        topLevelDest.add(R.id.home_fragment);
        topLevelDest.add(R.id.orders_fragment);
        topLevelDest.add(R.id.user_profile_fragment);
        topLevelDest.add(R.id.user_address_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDest)
                        .setDrawerLayout(drawerLayout)
                        .build();

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.cart){
            navController.navigate(R.id.action_open_cart);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();
        switch (id){

            case R.id.prescriptions:
                navController.navigate(R.id.orders_fragment);
                break;

            case R.id.orders:
                navController.navigate(R.id.orders_fragment);
                break;

            case R.id.user_profile:
                navController.navigate(R.id.user_profile_fragment);
                break;

            case R.id.user_address:
                navController.navigate(R.id.user_address_fragment);
                break;
        }
        return true;
    }
}

