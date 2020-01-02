package com.cti.lifego.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.CategoryAdapter;
import com.cti.lifego.content.Categories;
import com.cti.lifego.databinding.HomeFragmentBinding;
import com.cti.lifego.models.Category;
import com.cti.lifego.utils.NetworkUtil;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private Context mContext;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeFragmentBinding homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);

        Categories categories = new Categories();

        ArrayList<Category> categoryArrayList = new ArrayList<>(Arrays.asList(categories.CATEGORIES));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);

        homeFragmentBinding.categoryRecyclerView.setLayoutManager(linearLayoutManager);
        homeFragmentBinding.categoryRecyclerView.setAdapter(categoryAdapter);

        return homeFragmentBinding.getRoot();
    }

    private boolean isConnected(){
        return NetworkUtil.getConnectivityString(Objects.requireNonNull(mContext));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }
}
