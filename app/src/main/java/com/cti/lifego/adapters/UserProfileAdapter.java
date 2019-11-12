package com.cti.lifego.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cti.lifego.R;
import com.cti.lifego.fragments.AddressFragment;
import com.cti.lifego.fragments.UserDetailsFragment;

public class UserProfileAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public UserProfileAdapter(Context context, String[] values) {
        super(context, R.layout.list_view_item, values);
        this.context = context;
        this.values = values;
    }

    static class ViewHolder {
        private TextView item_name;
        private ImageView item_image;
    }

    @NonNull
    @Override
    public View getView(final int position, View rowItemView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = null;

        if (rowItemView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mViewHolder = new ViewHolder();

            rowItemView = inflater.inflate(R.layout.list_view_item, parent, false);

            mViewHolder.item_name = rowItemView.findViewById(R.id.label);
            mViewHolder.item_image = rowItemView.findViewById(R.id.list_image);
            
            mViewHolder.item_name.setText(values[position]);

            String s = values[position];
            switch (s) {
                case "Profile":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_profile);
                    break;
                case "Address":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_placeholder);
                    break;
                case "Help":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_help);
                    break;
                case "About":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_info);
                    break;
            }
        }
        else {
            mViewHolder = (ViewHolder) rowItemView.getTag();
        }

        rowItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = values[position];
                switch (s) {
                    case "Profile":
                        context.startActivity(new Intent(context, AddressFragment.class));
                        break;
                    case "Address":
                        context.startActivity(new Intent(context, UserDetailsFragment.class));
                        break;
                }
            }
        });
        
        return rowItemView;
    }

}