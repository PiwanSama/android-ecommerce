package com.cti.lifego.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cti.lifego.R;
import com.cti.lifego.databinding.MainActivityBinding;
import com.cti.lifego.databinding.NavHeaderBinding;
import com.cti.lifego.utils.NetworkUtil;
import com.cti.lifego.viewmodels.LoginViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavController navController;
    NavigationView navigationView;
    MainActivityBinding binding;
    LoginViewModel loginViewModel;
    DrawerLayout drawerLayout;
    MaterialSearchView searchView;
    Boolean start;
    AppBarConfiguration appBarConfiguration;
    NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected()){
            start = false;
            showSnack(findViewById(android.R.id.content), "No internet connection");
        }
        else {
            start = true;
            setUp();
        }
    }

    public void setUp(){
        setTheme(R.style.AppTheme);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        NavHeaderBinding navHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header, binding.navigationView, false);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        navHeaderBinding.setLoginViewModel(loginViewModel);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        drawerLayout = binding.drawerLayout;
        searchView = binding.searchView;
        navigationView = binding.navigationView;
        setUpNavigation();
        setUpSearch();
        navHostFragment  = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
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
            if (destination.getId() == R.id.personalDetailsFragment){
                makeFullScreen();
            }
            else if (destination.getId() == R.id.kinDetailsFragment){
                makeFullScreen();
            }
            else if (destination.getId() == R.id.loginFragment){
                makeFullScreen();
            }
            else{
                binding.appBar.setVisibility(View.VISIBLE);
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });

        Set<Integer> topLevelDestination = new HashSet<>();
        topLevelDestination.add(R.id.homeFragment);
        topLevelDestination.add(R.id.ordersFragment);
        topLevelDestination.add(R.id.userProfileFragment);
        topLevelDestination.add(R.id.cartFragment);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestination)
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
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();
        switch (id){
            case R.id.orders:
                if (navController.getCurrentDestination().getId() != R.id.ordersFragment) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (navHostFragment!=null){
            for (Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()){
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (navHostFragment!=null){
            for (Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()){
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    private boolean isConnected(){
        return NetworkUtil.getConnectivityString(this);
    }

    //Logs out user and redirects to login activity
    public static void logout() {
        //FirebaseAuth.getInstance().signOut();
    }

}

//Todo Check for wifi connection
