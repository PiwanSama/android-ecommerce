package com.horlobyte.ctipharmacy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.horlobyte.ctipharmacy.R;

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
    public View getView(int position, View rowItemView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = null;

        if (rowItemView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mViewHolder = new ViewHolder();

            rowItemView = inflater.inflate(R.layout.list_view_item, parent, false);

            mViewHolder.item_name = (TextView) rowItemView.findViewById(R.id.label);
            mViewHolder.item_image = (ImageView) rowItemView.findViewById(R.id.list_image);
            
            mViewHolder.item_name.setText(values[position]);

            String s = values[position];
            switch (s) {
                case "Address":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_location);
                    break;
                case "Payment Options":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_credit_card);
                    break;
                case "Account":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_account);
                    break;
                case "Help":
                    mViewHolder.item_image.setImageResource(R.drawable.ic_help);
                    break;
                default:
                    mViewHolder.item_image.setImageResource(R.drawable.ic_info);
                    break;
            }
        }
        else {
            mViewHolder = (ViewHolder) rowItemView.getTag();
        }
        
        return rowItemView;
    }
}