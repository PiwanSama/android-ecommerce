package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.adapters.OrdersAdapter;
import com.cti.lifego.databinding.OrdersFragmentBinding;
import com.cti.lifego.viewmodels.OrderViewModel;

import static android.view.View.GONE;

public class OrdersFragment extends BaseFragment implements OrdersAdapter.OrderClickListener {

    private OrdersFragmentBinding binding;
    private OrdersAdapter adapter;
    private OrderViewModel viewModel;
    private RecyclerView ordersRecyclerView;
    ProgressBar loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkConnected()){
            binding = DataBindingUtil.inflate(inflater, R.layout.orders_fragment, container, false);
            return binding.getRoot();
        }
        else {
            return inflater.inflate(R.layout.no_internet, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading = binding.loadingOrders;
        loading.setVisibility(View.VISIBLE);

        final NavController navController = Navigation.findNavController(view);
        ordersRecyclerView = binding.ordersRecyclerView;
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        viewModel.init();
        viewModel.getViewState().observe(getViewLifecycleOwner(), productViewState -> {
            switch (productViewState){
                case VIEW_ALL_ORDERS:
                    loadData();
                    break;
                case VIEW_SINGLE_ORDER:
                    navController.navigate(R.id.orderDetailFragment);
                    break;
            }
        });
    }

    private void loadData() {
        viewModel.listOrders().observe(getViewLifecycleOwner(), orders -> {
            adapter = new OrdersAdapter(orders, getContext());
            if (orders!=null){
                adapter.setOrders(orders);
                ordersRecyclerView.setAdapter(adapter);
            }
            loading.setVisibility(GONE);
            binding.ordersEmpty.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void getOrderId(String id) {
        viewModel.select(id);
    }
}
