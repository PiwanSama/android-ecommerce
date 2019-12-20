package com.cti.lifego.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cti.lifego.R;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.MainActivityBinding;
import com.cti.lifego.databinding.NavHeaderBinding;
import com.cti.lifego.intefaces.IMainActivity;
import com.cti.lifego.utils.NetworkUtil;
import com.cti.lifego.viewmodels.UserViewModel;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {
    NavController navController;
    NavigationView navigationView;
    MainActivityBinding binding;
    UserViewModel userViewModel;
    DrawerLayout drawerLayout;
    MaterialSearchView searchView;
    Boolean start;
    AppBarConfiguration appBarConfiguration;
    TextView cartItemCount;
    int mCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected()){
            start = false;
            showSnack(findViewById(android.R.id.content), "No internet connection");
        }
        else {
            start = true;
            setTheme(R.style.AppTheme);
            binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
            NavHeaderBinding navHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header, binding.navigationView, false);
            binding.navigationView.addHeaderView(navHeaderBinding.getRoot());
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            navHeaderBinding.setUserViewModel(userViewModel);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            drawerLayout = binding.drawerLayout;
            searchView = binding.searchView;
            navigationView = binding.navigationView;
            setUpNavigation();
            setUpSearch();
        }
    }

    private void showSnack(View v, String s){
        Snackbar snackbar = Snackbar.make(v, s, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.show();
    }

    private void setUpSearch() {
        binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
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

        binding.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
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

        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.registration_fragment){
                makeFullScreen();
            }
            else if (destination.getId() == R.id.login_fragment){
                makeFullScreen();
            }
            else{
                binding.appBar.setVisibility(View.VISIBLE);
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });

        Set<Integer> topLevelDest = new HashSet<>();
        topLevelDest.add(R.id.home_fragment);
        topLevelDest.add(R.id.orders_fragment);
        topLevelDest.add(R.id.user_profile_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDest)
                .setDrawerLayout(binding.drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void makeFullScreen(){
        binding.appBar.setVisibility(View.GONE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    @Override
    public void onBackPressed() {
        if (start){
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
        else{
            super.onBackPressed();
            this.finish();
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

    private void setupBadge() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> productIDs = preferences.getStringSet(PreferenceKeys.shopping_cart, new HashSet<String>());
        mCartItemCount = productIDs.size();

        if (cartItemCount != null) {
            if (mCartItemCount == 0) {
                if (cartItemCount.getVisibility() != View.GONE) {
                    cartItemCount.setVisibility(View.GONE);
                }
            } else {
                cartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                /*if (cartItemCount.getVisibility() != View.VISIBLE) {
                    cartItemCount.setVisibility(View.VISIBLE);
                }*/
            }
        }
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
        }
        return false;
    }

    private boolean isConnected(){
        return NetworkUtil.getConnectivityString(this);
    }

    //Logs out user and redirects to login activity
    public static void logout() {
        //FirebaseAuth.getInstance().signOut();
    }

}

