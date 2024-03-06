package com.example.dicerollapp.navigation_drawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dicerollapp.R;

public class DrawerItemCustomAdapter extends ArrayAdapter<DataModel> {

    Context mContext;
    int layoutResourceId;
    DataModel[] data = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, DataModel[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_row, parent, false);
        }

        ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
        TextView textViewName = convertView.findViewById(R.id.textViewName);

        DataModel folder = getItem(position);


//        imageViewIcon.setImageResource(folder.icon);
//        textViewName.setText(folder.name);
        // Set the icon
        if (folder != null) {
            imageViewIcon.setImageResource(folder.getIcon());
        }

        // Set the name dynamically
        if (folder != null) {
            textViewName.setText(folder.getName());
        }

        return convertView;
    }
}
