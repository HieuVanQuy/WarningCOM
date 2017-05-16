package com.x23_team.warningcom.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.x23_team.warningcom.Entity.Post;
import com.x23_team.warningcom.R;

import java.util.ArrayList;

/**
 * Created by danam on 07/04/2017.
 */

public class NotificationAdapter extends ArrayAdapter<Post> {
    Activity context = null;
    ArrayList<Post> myArray = null;
    int LayoutId;
    String output="";

    public NotificationAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);

        this.context = context;
        this.LayoutId = resource;
        this.myArray = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId,null);
        final Post myPost = myArray.get(position);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.iconNotificationListview);
        switch (myPost.getType_id()){
            case "1": {imageView.setImageResource(R.drawable.giaothong);
                output+="Traffic";}
                break;
            case "2": {imageView.setImageResource(R.drawable.wheather);
                output+="Wheather";}
                break;
            case "3": {imageView.setImageResource(R.drawable.helpme);
                output+="Help me";}
                break;
        }
        output = "";

        final TextView txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
        txtAddress.setText(myPost.getAddress());
        return convertView;
    }
}
