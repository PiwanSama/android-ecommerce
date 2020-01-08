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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.BrowseStoreAdapter;
import com.cti.lifego.databinding.FragmentBrowseStoreBinding;
import com.cti.lifego.models.Product;
import com.cti.lifego.viewmodels.ProductViewModel;

public class BrowseStoreFragment extends BaseFragment{

    private FragmentBrowseStoreBinding binding;
    private BrowseStoreAdapter adapter;
    private ProductViewModel viewModel;
    private RecyclerView productRecyclerView;
    private String storeID;

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

        final NavController navController = Navigation.findNavController(view);
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
                        adapter = new BrowseStoreAdapter(products, getContext(), new BrowseStoreAdapter.ProductClickListener() {
                            @Override
                            public void getProductId(Product product) {
                                NavController controller = Navigation.findNavController(view);
                                BrowseStoreFragmentDirections.ActionShowProductDetail action = BrowseStoreFragmentDirections.actionShowProductDetail(String.valueOf(product.getId()));
                                controller.navigate(action);
                            }
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
    }

    private void setSelectedStore(String id){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
    }
}
