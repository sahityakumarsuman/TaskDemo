package com.example.app.taskdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.app.taskdemo.R;
import com.example.app.taskdemo.app_controler.AppController;
import com.example.app.taskdemo.places_utill.PlaceDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duke on 12/29/2015.
 */
public class PlaceAdapter extends BaseAdapter {
    Activity _activity;

    List<PlaceDetail> _place;

    LayoutInflater inflater;


    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public PlaceAdapter(Activity activit, List<PlaceDetail> place) {
        _place = new ArrayList<>();
        this._activity = activit;
        this._place = place;
    }


    @Override
    public int getCount() {
        return _place.size();
    }

    @Override
    public Object getItem(int position) {
        return _place.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.places_item, null);
        }

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView palce_name = (TextView) convertView.findViewById(R.id.place_name_text);
        TextView palce_address = (TextView) convertView.findViewById(R.id.place_address);
        TextView place_verified_or_not = (TextView) convertView.findViewById(R.id.verified_or_not);
        NetworkImageView placeImage = (NetworkImageView) convertView.findViewById(R.id.place_image);
        TextView checkInData = (TextView) convertView.findViewById(R.id.check_in);
        // ---------------------------------------------------------------------------------------
        PlaceDetail placeDetail = _place.get(position);
        palce_name.setText(placeDetail.getPlaceName());
        palce_address.setText(placeDetail.getPlaceAddress());
        place_verified_or_not.setText(placeDetail.getPlaceVerefiedOrNot());
        checkInData.setText("Check In " + placeDetail.get_checkIn());
        placeImage.setImageUrl(placeDetail.getPlaceImageUrl(), imageLoader);
        return convertView;

    }
}
