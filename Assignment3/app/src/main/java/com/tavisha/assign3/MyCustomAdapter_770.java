package com.tavisha.assign3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyCustomAdapter_770 extends ArrayAdapter<Friends_770> {

    private ArrayList<Friends_770> friendsArrayList;
    Context context;

    public MyCustomAdapter_770(ArrayList<Friends_770> friendsArrayList, Context context) {
        super(context, R.layout.items_list_layout_770, friendsArrayList);
        this.friendsArrayList = friendsArrayList;
        this.context = context;
    }

    // View Holder Class: used to cache references to the views within
    private static class MyViewHolder {
        TextView name;
        TextView location;
        ImageView friendImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1- Get the friend object for the current position
        Friends_770 friend = getItem(position);

        // 2- Inflate Layout
        MyViewHolder myViewHolder;
        final View result;

        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.items_list_layout_770, parent, false);

            // Finding Views
            myViewHolder.name = convertView.findViewById(R.id.name770); // Ensure updated IDs
            myViewHolder.location = convertView.findViewById(R.id.location770); // Ensure updated IDs
            myViewHolder.friendImage = convertView.findViewById(R.id.imageView770); // Ensure updated IDs

            result = convertView;
            convertView.setTag(myViewHolder);
        } else {
            // The view is recycled
            myViewHolder = (MyViewHolder) convertView.getTag();
            result = convertView;
        }

        // Getting the data from Friends_770 model class
        myViewHolder.name.setText(friend.getName());
        myViewHolder.location.setText(friend.getLocation());
        myViewHolder.friendImage.setImageResource(friend.getImageResourceId());

        return result;
    }
}

