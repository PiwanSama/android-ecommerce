package com.cti.lifego.activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cti.lifego.R;
import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    MaterialSearchView searchView;
    TextView cartItemCount;
    int mCartItemCount = 10;
    Boolean mLocationPermissionGranted;
    int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getLocationPermission();
        isLocationEnabled();

        toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.search_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setUpNavigation();
        setUpSearch();
        getLocationPermission();
        isLocationEnabled();
    }

    private void setUpSearch() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
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
        if (searchView.isSearchOpen()){
            searchView.closeSearch();
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
        MenuItem search_item = menu.findItem(R.id.search);
        searchView.setMenuItem(search_item);

        final MenuItem cart_item = menu.findItem(R.id.cart);
        final View cartItemView = menu.findItem(R.id.cart).getActionView();
        cartItemCount = cartItemView.findViewById(R.id.cart_badge);
        setupBadge();
        cartItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cart_item);
            }
        });

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
            case R.id.orders:
                if (navController.getCurrentDestination().getId() != R.id.orders_fragment) {
                    navController.navigate(R.id.action_homeFragment_to_ordersFragment);
            }
                break;

            case R.id.user_profile:
                if (navController.getCurrentDestination().getId() != R.id.user_profile) {
                    navController.navigate(R.id.action_homeFragment_to_profileFragment);
                }
                break;

            case R.id.user_address:
                if (navController.getCurrentDestination().getId() != R.id.user_address) {
                    navController.navigate(R.id.action_homeFragment_to_addressFragment);
                }
                break;
        }
        return false;
    }

    private void setupBadge() {

        if (cartItemCount != null) {
            if (mCartItemCount == 0) {
                if (cartItemCount.getVisibility() != View.GONE) {
                    cartItemCount.setVisibility(View.GONE);
                }
            } else {
                cartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (cartItemCount.getVisibility() != View.VISIBLE) {
                    cartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private boolean isLocationEnabled() {

        LocationManager locationManager = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER);
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    //Logs out user and redirects to login activity
    public static void logout() {
        //FirebaseAuth.getInstance().signOut();
    }
}

