package com.cti.lifego.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.BrowseStoreAdapter;
import com.cti.lifego.content.PreferenceKeys;
import com.cti.lifego.databinding.FragmentBrowseStoreBinding;
import com.cti.lifego.models.Cart;
import com.cti.lifego.utils.CartHelper;
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.Objects;

public class BrowseStoreFragment extends BaseFragment{

    private FragmentBrowseStoreBinding binding;
    private BrowseStoreAdapter adapter;
    private ProductViewModel viewModel;
    private RecyclerView productRecyclerView;
    private String storeID, storeName;
    private Cart cart;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkConnected()){
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse_store, container, false);
            getViewOrderFragment();
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

        assert getArguments() != null;
        storeID = BrowseStoreFragmentArgs.fromBundle(getArguments()).getStoreID();
        storeName = getSelectedStore();

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewModel.listProducts(storeID);

        binding.browseStoreRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.browseStoreRecyclerView.setAdapter(adapter);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        showWarningDialog(storeName);
                    }
                });

    }

    private void getViewOrderFragment(){
        FragmentManager fm  = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        LinearLayout floating_order = binding.floatingCardHolder;
        int layout_id = floating_order.getId();
        cart = CartHelper.getCart();
        if (cart.isEmpty()){
                    transaction.replace(layout_id, new FloatingCardOrderFragment());
                    transaction.commit();
            }else {
                Log.i("Browse", "Null");
            }
        }

    private void showWarningDialog(String storeName){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("You are leaving "+storeName);
        builder.setMessage("Leaving this store will empty your cart");
        builder.setPositiveButton("Yes, I'm sure", (dialog, which) -> {
            dialog.dismiss();
            if (cart.isEmpty()){
                cart.clear();
            }
            navController.popBackStack(R.id.storesListFragment, false);
        });
        builder.setNegativeButton("No thanks", (dialog, which) -> {

        });
        builder.setCancelable(false);
        builder.show();
    }

    private String getSelectedStore(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString(PreferenceKeys.selectedStore, "");
    }
}
