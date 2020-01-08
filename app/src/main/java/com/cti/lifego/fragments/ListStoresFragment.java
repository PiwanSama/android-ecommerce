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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.StoresAdapter;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.ListStoresBinding;
import com.cti.lifego.models.Store;
import com.cti.lifego.viewmodels.StoreViewModel;

public class ListStoresFragment extends BaseFragment{

    private ListStoresBinding binding;
    private StoresAdapter adapter;
    private StoreViewModel viewModel;
    private RecyclerView storesRecyclerView;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkConnected()){
            binding = DataBindingUtil.inflate(inflater, R.layout.list_stores, container, false);
            binding.storeShimmer.startShimmer();
            return binding.getRoot();
        }
        else {
            return inflater.inflate(R.layout.no_internet, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSelectedStore();
        navController = Navigation.findNavController(view);
        final NavController navController = Navigation.findNavController(view);
        storesRecyclerView = binding.storesRecyclerView;
        storesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        assert getArguments() != null;
        int categoryID = ListStoresFragmentArgs.fromBundle(getArguments()).getCategoryID();

        viewModel.init();
        viewModel.getViewState().observe(getViewLifecycleOwner(), storeViewState -> {
            switch (storeViewState){
                case VIEW_ALL_STORES:
                    viewModel.listStores(categoryID).observe(getViewLifecycleOwner(), stores -> {
                        adapter = new StoresAdapter(stores, getContext(), new StoresAdapter.StoreClickListener() {
                            @Override
                            public void getStoreId(Store store) {
                                NavController navController = Navigation.findNavController(view);
                                ListStoresFragmentDirections.ActionStoresListFragmentToBrowseStoreFragment action = ListStoresFragmentDirections.actionStoresListFragmentToBrowseStoreFragment(String.valueOf(store.getId()));
                            }
                        });
                        if (stores != null) {
                            adapter.setStores(stores);
                            storesRecyclerView.setAdapter(adapter);
                            binding.storeShimmer.stopShimmer();
                            binding.storeShimmer.setVisibility(View.INVISIBLE);
                        }
                    });
                    break;
                case VIEW_SINGLE_STORE:
                    navController.navigate(R.id.browseStoreFragment);
                    break;
            }
        });
    }

    private void getSelectedStore(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String category = preferences.getString(PreferenceKeys.selectedStore, "");
        Log.i("Current Cat", category);
    }

}
