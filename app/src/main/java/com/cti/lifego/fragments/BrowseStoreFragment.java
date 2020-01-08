package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.BrowseStoreAdapter;
import com.cti.lifego.databinding.FragmentBrowseStoreBinding;
import com.cti.lifego.models.Cart;
import com.cti.lifego.utils.CartHelper;
import com.cti.lifego.viewmodels.ProductViewModel;

public class BrowseStoreFragment extends BaseFragment{

    private FragmentBrowseStoreBinding binding;
    private BrowseStoreAdapter adapter;
    private ProductViewModel viewModel;
    private RecyclerView productRecyclerView;
    private String storeID;
    private Cart cart;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkConnected()){
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse_store, container, false);
            return binding.getRoot();
        }
        else {
            return inflater.inflate(R.layout.no_internet, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        storeID = BrowseStoreFragmentArgs.fromBundle(getArguments()).getStoreID();

        getViewOrderFragment();

        navController = Navigation.findNavController(view);
        productRecyclerView = binding.browseStoreRecyclerView;
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setProductViewModel(viewModel);
        viewModel.init();
        viewModel.getViewState().observe(getViewLifecycleOwner(), productViewState -> {
            switch (productViewState){
                case VIEW_ALL_PRODUCTS:
                    assert getArguments() != null;
                    viewModel.listProducts(storeID).observe(getViewLifecycleOwner(), products -> {
                        adapter = new BrowseStoreAdapter(products, getContext(), product -> {
                            BrowseStoreFragmentDirections.ActionShowProductDetail action = BrowseStoreFragmentDirections.actionShowProductDetail(String.valueOf(product.getId()));
                            navController.navigate(action);
                        });
                        if (products!=null){
                            adapter.setProducts(products);
                            productRecyclerView.setAdapter(adapter);
                        }
                    });
                    break;
                case VIEW_SINGLE_PRODUCT:
                    navController.navigate(R.id.productDetailFragment);
                    break;
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        showWarningDialog(storeID);
                    }
                });
    }

    void getViewOrderFragment(){
        LinearLayout floating_order = binding.floatingCardHolder;
        cart = CartHelper.getCart();
        if (!cart.isEmpty()){
            floating_order.setVisibility(View.VISIBLE);
        }
    }


    private void showWarningDialog(String storeName){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("You are leaving "+storeName);
        builder.setMessage("Leaving this store will empty your cart");
        builder.setPositiveButton("Yes, I'm sure", (dialog, which) -> {
            dialog.dismiss();

            cart.clear();

            navController.popBackStack(R.id.homeFragment, false);
        });
        builder.setNegativeButton("No thanks", (dialog, which) -> {

        });
        builder.setCancelable(false);
        builder.show();
    }

}
