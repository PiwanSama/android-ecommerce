package com.cti.lifego.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.cti.lifego.databinding.FragmentListStoresBinding;
import com.cti.lifego.viewmodels.StoreViewModel;

public class ListStoresFragment extends BaseFragment{

    private FragmentListStoresBinding binding;
    private StoresAdapter adapter;
    private StoreViewModel viewModel;
    private RecyclerView storesRecyclerView;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkConnected()){
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_stores, container, false);
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
                        adapter = new StoresAdapter(getContext(), stores, store -> {
                            setSelectedStore(store.getName());
                            ListStoresFragmentDirections.ActionBrowseStore action = ListStoresFragmentDirections.actionBrowseStore(String.valueOf(store.getId()));
                            navController.navigate(action);
                        });
                        if (stores != null) {
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

    private void setSelectedStore(String storeName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PreferenceKeys.selectedStore, storeName);
        editor.apply();
    }
}
