package com.example.app.taskdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Duke on 12/29/2015.
 */
public class ShowPlaceDetails extends Fragment {

    private TextView placeName, placeAddress, placeVerification, placeCheckIns, placeFormatedAddress;


    public ShowPlaceDetails() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.place_details, container, false);
        placeName = (TextView) view.findViewById(R.id.place_name_text_fragment);
        placeAddress = (TextView) view.findViewById(R.id.place_address_fragment_hh);
        placeVerification = (TextView) view.findViewById(R.id.place_verification_fragment);
        placeCheckIns = (TextView) view.findViewById(R.id.place_checkIn_fragment);
        placeFormatedAddress = (TextView) view.findViewById(R.id.place_formatedAddress_fragment);


        String place_name, place_address, place_verification;
        int place_checkins;
        Bundle getData = this.getArguments();
        if (getData == null) {
            place_name = "";
            place_address = "";
            place_verification = "";
            place_checkins = 0;
        } else {

            place_name = getData.getString("place_name");
            place_address = getData.getString("place_address");
            place_verification = getData.getString("place_verify");
            place_checkins = getData.getInt("checkin");

            placeName.setText("Place Name is :  " + place_name);
            placeAddress.setText("Place Address is :  " + place_address);
            placeVerification.setText("Place Verification is :  " + place_verification);
            placeCheckIns.setText("Place CheckIns  : " + place_checkins);

        }


        return view;
    }


    public static ShowPlaceDetails newInstance() {

        return (new ShowPlaceDetails());

    }
}
