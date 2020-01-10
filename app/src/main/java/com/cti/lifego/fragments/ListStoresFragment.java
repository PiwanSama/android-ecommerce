package com.cti.lifego.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cti.lifego.R;
import com.cti.lifego.adapters.StoresAdapter;
import com.cti.lifego.content.Pharms;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.content.Stores;
import com.cti.lifego.databinding.ListStoresBinding;
import com.cti.lifego.models.Store;

import java.util.ArrayList;
import java.util.Arrays;

public class ListStoresFragment extends BaseFragment{

    private ListStoresBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkConnected()){
            binding = DataBindingUtil.inflate(inflater, R.layout.list_stores, container, false);
           // binding.storeShimmer.startShimmer();
            return binding.getRoot();
        }
        else {
            return inflater.inflate(R.layout.no_internet, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);

        assert getArguments() != null;
        int categoryID = ListStoresFragmentArgs.fromBundle(getArguments()).getCategoryID();

        Log.i("Category ID", String.valueOf(categoryID));

        Stores mStores = new Stores();
        ArrayList<Store> storeArrayList = new ArrayList<>(Arrays.asList(mStores.STORES));

        Pharms mPharms = new Pharms();
        ArrayList<Store> pharmArrayList = new ArrayList<>(Arrays.asList(mPharms.PHARMS));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        StoresAdapter adapter = null;

        if (categoryID == 1){
            adapter = new StoresAdapter(storeArrayList, new StoresAdapter.StoreClickListener() {
                @Override
                public void getStoreId(Store store) {
                    saveStore(store.getName());
                    ListStoresFragmentDirections.ActionStoresListFragmentToBrowseStoreFragment action = ListStoresFragmentDirections.actionStoresListFragmentToBrowseStoreFragment(store.getName());
                    navController.navigate(action);
                }
            });
        }else if(categoryID == 3){
            adapter = new StoresAdapter(pharmArrayList, new StoresAdapter.StoreClickListener() {
                @Override
                public void getStoreId(Store store) {
                    saveStore(store.getName());
                    ListStoresFragmentDirections.ActionStoresListFragmentToBrowseStoreFragment action = ListStoresFragmentDirections.actionStoresListFragmentToBrowseStoreFragment(store.getName());
                    navController.navigate(action);
                }
            });
        }

        binding.storesRecyclerView.setLayoutManager(linearLayoutManager);
        binding.storesRecyclerView.setAdapter(adapter);
       // binding.storeShimmer.stopShimmer();
      //  binding.storeShimmer.setVisibility(View.INVISIBLE);
    }

    private void saveStore(String storeName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PreferenceKeys.selectedStore, String.valueOf(storeName));
        editor.apply();

        Log.i("STORE", String.valueOf(storeName));
    }
}
