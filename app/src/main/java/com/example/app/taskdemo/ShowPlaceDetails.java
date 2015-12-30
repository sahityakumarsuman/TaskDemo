package com.example.app.taskdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Duke on 12/29/2015.
 */
public class ShowPlaceDetails extends Fragment {

    private TextView placeName, placeAddress, placeVerification, placeSummry, placeFormatedAddress;
    Bundle getData;

    public ShowPlaceDetails() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData = savedInstanceState;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.place_details, container, false);
        placeName = (TextView) view.findViewById(R.id.place_name_text_fragment);
        placeAddress = (TextView) view.findViewById(R.id.place_address_fragment_hh);
        placeVerification = (TextView) view.findViewById(R.id.place_verification_fragment);
        placeSummry = (TextView) view.findViewById(R.id.place_summry_fragment);
        placeFormatedAddress = (TextView) view.findViewById(R.id.place_formatedAddress_fragment);


//        String data = this.getArguments().getString("place_name");
//        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
        //+ getArguments().getString("place_name")
        //+ getArguments().getString("place_address")
        //+ getArguments().getString("place_verify")
        placeName.setText("Place Name is : ");
        placeAddress.setText("Place Address is : ");
        placeVerification.setText("Place Verification is :");

        return view;
    }


    public static ShowPlaceDetails newInstance() {

        return (new ShowPlaceDetails());

    }
}
