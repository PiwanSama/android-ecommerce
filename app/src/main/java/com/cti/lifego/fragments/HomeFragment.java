package com.cti.lifego.fragments;

import android.os.Bundle;
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

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryArrayList, new CategoryAdapter.CategoryClickListener() {
            @Override
            public void getCategoryId(Category category) {
                NavController controller = Navigation.findNavController(view);
                HomeFragmentDirections.ActionHomeFragmentToStoresListFragment action = HomeFragmentDirections.actionHomeFragmentToStoresListFragment(category.getId());
                controller.navigate(action);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        binding.categoryRecyclerView.setLayoutManager(linearLayoutManager);
        binding.categoryRecyclerView.setAdapter(categoryAdapter);
    }
}
