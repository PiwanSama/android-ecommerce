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
import com.cti.lifego.adapters.CategoryAdapter;
import com.cti.lifego.content.Categories;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.HomeFragmentBinding;
import com.cti.lifego.models.Category;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends BaseFragment{

    HomeFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isNetworkConnected()) {
            binding = DataBindingUtil.inflate(inflater, R.layout.no_internet, container, false);
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Categories categories = new Categories();
        ArrayList<Category> categoryArrayList = new ArrayList<>(Arrays.asList(categories.CATEGORIES));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryArrayList, category -> {
            saveCategory(category.getId());
            NavController controller = Navigation.findNavController(view);
            HomeFragmentDirections.ActionListStores action = HomeFragmentDirections.actionListStores(category.getId());
            controller.navigate(action);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        binding.categoryRecyclerView.setLayoutManager(linearLayoutManager);
        binding.categoryRecyclerView.setAdapter(categoryAdapter);
    }


    private void saveCategory(int catID){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PreferenceKeys.selectedStore, String.valueOf(catID));
        editor.apply();

        Log.i("STORE", String.valueOf(catID));
    }
}
