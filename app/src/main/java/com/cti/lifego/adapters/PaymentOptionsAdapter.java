/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.models.PaymentOption;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */
public class PaymentOptionsAdapter extends RecyclerView.Adapter<PaymentOptionsAdapter.ViewHolder>{

    private ArrayList<PaymentOption> payment_options;
    private Context context;
    private PaymentClickListener listener;
    private static int lastChecked = 0;

    public PaymentOptionsAdapter(Context context, ArrayList<PaymentOption> payment_options, PaymentClickListener listener){
        this.payment_options = payment_options;
        this.context = context;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public MaterialCardView card;
        public TextView name;
        public ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = (MaterialCardView) itemView.findViewById(R.id.payment_card_item);
            name = (TextView) itemView.findViewById(R.id.payment_item_name);
            image = (ImageView) itemView.findViewById(R.id.payment_item_image);
        }

        void bind(PaymentOption option, PaymentClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.getSelectedPayment(option);
                }
            });
        }
    }

    @NonNull
    @Override
    public PaymentOptionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_option_list_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentOption paymentOption = payment_options.get(position);
        holder.name.setText(paymentOption.getName());
        holder.image.setImageResource(paymentOption.getImage());
        if (position == lastChecked) {
            holder.card.setChecked(true);
        } else {
            holder.card.setChecked(false);
        }
        holder.bind(paymentOption, listener);
    }

    @Override
    public int getItemCount() {
        return payment_options.size();
    }

    public interface PaymentClickListener{
        void getSelectedPayment(PaymentOption option);
    }
}