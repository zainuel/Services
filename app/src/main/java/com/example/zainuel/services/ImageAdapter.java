package com.example.zainuel.services;

/**
 * Created by ZAINUEL on 4/10/2017.
 */

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int layoutResourceId;

    // Constructor
    public ImageAdapter(Context c,int layoutResourceId) {
        mContext = c;
        this.layoutResourceId=layoutResourceId;


    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.air_conditioner, R.drawable.ic_camera_alt_black_36dp,
            R.drawable.ic_camera_alt_black_36dp2, R.drawable.alphabetical,R.drawable.ic_tv_black_36dp,R.drawable.android,R.drawable.hotel,R.drawable.ic_directions_bike_black_36dp

    };
}