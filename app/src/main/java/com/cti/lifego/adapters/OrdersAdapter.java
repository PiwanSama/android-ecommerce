/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.databinding.OrderListItemBinding;
import com.cti.lifego.models.Order;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>{

    private List<Order> orderList;
    private Context mContext;
    private OrderListItemBinding binding;
    private OrderClickListener listener;

    public OrdersAdapter(List<Order> orderList, Context context){
        this.orderList = orderList;
        this.mContext = context;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{

        private OrderListItemBinding itemBinding;

        OrderViewHolder(@NonNull OrderListItemBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        void bind(Order order, OrderClickListener mListener){
            itemBinding.setOrder(order);
            itemView.setOnClickListener(v -> mListener.getOrderId(String.valueOf(order.getId())));
            itemBinding.executePendingBindings();
        }
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_item, parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int position) {
        Order order = orderList.get(position);
        orderViewHolder.bind(order, listener);
    }

    public void setOrders(List<Order> products){
        orderList = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OrderClickListener {
        void getOrderId(String id);
    }
}
