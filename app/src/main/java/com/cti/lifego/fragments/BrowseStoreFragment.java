package com.cti.lifego.fragments;

import android.os.Bundle;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.BrowseStoreAdapter;
import com.cti.lifego.content.Groceries;
import com.cti.lifego.content.Medicals;
import com.cti.lifego.databinding.FragmentBrowseStoreBinding;
import com.cti.lifego.models.Cart;
import com.cti.lifego.models.Product;
import com.cti.lifego.utils.CartHelper;
import com.cti.lifego.viewmodels.ProductViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BrowseStoreFragment extends BaseFragment{

    private FragmentBrowseStoreBinding binding;
    private BrowseStoreAdapter adapter;
    private ProductViewModel viewModel;
    private RecyclerView productRecyclerView;
    private String storeID;
    private Cart cart;
    private NavController navController;
    private String storePref;

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
        storePref = BrowseStoreFragmentArgs.fromBundle(getArguments()).getStoreID();

        Log.i("STOREY", storePref);

        Groceries mGroceries = new Groceries();
        ArrayList<Product> grocArrayList = new ArrayList<>(Arrays.asList(mGroceries.PHARMS));

        Medicals mMeds = new Medicals();
        ArrayList<Product> medArrayList = new ArrayList<>(Arrays.asList(mMeds.MEDS));

        BrowseStoreAdapter adapter = null;

        if (storePref == "Fresco Supermarket"){
            adapter = new BrowseStoreAdapter(grocArrayList, product -> {
                cart.add(product, 1);
                Log.i("Adding....", product.getName());
                getViewOrderFragment();
            });

        }else if(storePref == "Nasser Pharmacy"){
            adapter = new BrowseStoreAdapter(medArrayList, product -> {
                cart.add(product, 1);
                getViewOrderFragment();
            });
        }

        binding.browseStoreRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.browseStoreRecyclerView.setAdapter(adapter);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        showWarningDialog(storePref);
                    }
                });

    }

    private void getViewOrderFragment(){
        FragmentManager fm  = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        LinearLayout floating_order = binding.floatingCardHolder;
        int layout_id = floating_order.getId();
        cart = CartHelper.getCart();
        if (!cart.isEmpty()){
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
            if (!cart.isEmpty()){
                cart.clear();
            }
            navController.popBackStack(R.id.storesListFragment, false);
        });
        builder.setNegativeButton("No thanks", (dialog, which) -> {

        });
        builder.setCancelable(false);
        builder.show();
    }
}
