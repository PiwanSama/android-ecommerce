/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cti.lifego.R;
import com.cti.lifego.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements RecyclerView.OnClickListener{

    private ArrayList<Category> categories;

    public CategoryAdapter(ArrayList<Category> categories){
        this.categories = categories;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name, description;
        public ImageView image;
        public int id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category_name);
            description = (TextView) itemView.findViewById(R.id.category_description);
            image = (ImageView) itemView.findViewById(R.id.category_image);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
    return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.name.setText(category.getName());
        holder.description.setText(category.getDescription());
        holder.image.setImageResource(category.getImage());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onClick(View v) {

    }
}
