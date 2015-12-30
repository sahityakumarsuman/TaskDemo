package com.example.app.taskdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.app.taskdemo.adapter.PlaceAdapter;
import com.example.app.taskdemo.app_controler.AppController;
import com.example.app.taskdemo.app_controler.InternetConnectionDetector;
import com.example.app.taskdemo.mobile_database.DatabaseHandler;
import com.example.app.taskdemo.places_utill.PlaceDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duke on 12/29/2015.
 */
public class PlacesAfterSearch extends ActionBarActivity {


    String apiString = "";

    private ListView listViewData;
    private TextView placeSearch;
    private Button backButton;

    List<PlaceDetail> _placeList = new ArrayList<>();

    PlaceAdapter adapterList;
    String _query = "";


    DatabaseHandler db;

    InternetConnectionDetector internetConnection;

    FrameLayout listviewLayout, simpleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_listview_result);

        db = new DatabaseHandler(PlacesAfterSearch.this);
        internetConnection = new InternetConnectionDetector(PlacesAfterSearch.this);

        Intent getdataSearched = getIntent();
        _query = getdataSearched.getStringExtra("query");
        placeSearch = (TextView) findViewById(R.id.result_show_text);

        placeSearch.setText("Places for " + _query + " in Gurgaon is :");

        listViewData = (ListView) findViewById(R.id.listView_show_places_with_image);
        backButton = (Button) findViewById(R.id.back_button);

        // layouts
        listviewLayout = (FrameLayout) findViewById(R.id.list_view_frame_layout);
        simpleLayout = (FrameLayout) findViewById(R.id.fragment_frame_layout);


        if (!internetConnection.isConnectingToInternet()) {

            showMessage("Internet not available ---> Previous Search Results <--- ");
            _placeList.clear();

            _placeList = db.getAllPlaces();

            for (PlaceDetail pd : _placeList) {

                String data = "database --> name : " + pd.getPlaceName() + " address : " + pd.getPlaceAddress() + " Image path: " + pd.getPlaceImageUrl() + " verification : " + pd.getPlaceVerefiedOrNot();

                Log.d("name is : ", data);
            }

        } else {

            // If internet available then clear the previous data form the table 'place' so that new data can be stored into it
            db.clearDataFromTable();
            showMessage("Internet available ---> New Search Results <--- ");

            new ParseDataToList().execute();

        }
        adapterList = new PlaceAdapter(PlacesAfterSearch.this, _placeList);

        listViewData.setAdapter(adapterList);
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                simpleLayout.setVisibility(View.VISIBLE);
                listviewLayout.setVisibility(View.GONE);
                backButton.setVisibility(View.VISIBLE);

                PlaceDetail data = (PlaceDetail) adapterList.getItem(position);
                String place_name = data.getPlaceName();

                String place_address = data.getPlaceAddress();
                String image_path = data.getPlaceImageUrl();
                String place_verification = data.getPlaceVerefiedOrNot();
                showMessage("Place name " + place_name + " place address" + place_address + " Place Verificaiton" + place_verification);
                ShowPlaceDetails show_data_fragment = new ShowPlaceDetails();
                Bundle bundle = new Bundle();
                bundle.putString("place_name", place_name);
                bundle.putString("place_address", place_address);
                bundle.putString("place_verify", place_verification);
                bundle.putString("imagePath", image_path);
                show_data_fragment.setArguments(bundle);
                ShowPlaceDetails placeFragment = (ShowPlaceDetails) getSupportFragmentManager().findFragmentByTag("fragment_tag");
                if (placeFragment == null)
                    showFragment();


            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleLayout.setVisibility(View.GONE);
                listviewLayout.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.GONE);
            }
        });


    }

    private void showFragment() {


        ShowPlaceDetails placeFragment = ShowPlaceDetails.newInstance();
        FragmentTransaction transact = getSupportFragmentManager().beginTransaction();
        transact.add(R.id.fragment_show, placeFragment, "fragment_tag");
        transact.commit();

    }

    private class ParseDataToList extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showMessage("Please wait..");
            apiString = "https://api.foursquare.com/v2/venues/search?ll=28.4700,77.0300&query=" + _query + "&oauth_token=PBTK4K5BQ21HJSH5MJOBDINBKCK3MJNT1DL4IFQEJJ40CB4S&v=20151229";
            Log.d("api Call", apiString);
            Log.d("Query call", _query);

        }

        @Override
        protected Void doInBackground(Void... params) {

            String tag_string_req = "list_view_data";


            StringRequest strReq = new StringRequest(Request.Method.GET, apiString, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject responseJsonObject = jsonObject.getJSONObject("response");

                        JSONArray venueJsonArray = responseJsonObject.getJSONArray("venues");
                        showMessage("Venues length " + venueJsonArray.length());

                        int length_venues_array = venueJsonArray.length();

                        String name = "", palceAddress = "", imagePath = "", verify = "";


                        for (int i = 0; i < length_venues_array; i++) {
                            if (i < 20) {


                                JSONObject venuInsideJSONobject = venueJsonArray.getJSONObject(i);
                                PlaceDetail placeDetail = new PlaceDetail();
                                placeDetail.setPlaceName(venuInsideJSONobject.getString("name"));
                                name = venuInsideJSONobject.getString("name");
                                imagePath = "path";
                                Log.d("name ", i + " " + venuInsideJSONobject.getString("name"));
                                JSONObject locationObject = venuInsideJSONobject.getJSONObject("location");
                                placeDetail.setPlaceAddress(locationObject.getString("address"));
                                palceAddress = locationObject.getString("address");
                                if (venuInsideJSONobject.getBoolean("verified")) {

                                    placeDetail.setPlaceVerfication("Verified");
                                    verify = "Verified";
                                } else if (!venuInsideJSONobject.getBoolean("verified")) {
                                    placeDetail.setPlaceVerfication("Not Verified");
                                    verify = "Not Verified";
                                } else {
                                    placeDetail.setPlaceVerfication("Verification Not Available");
                                    verify = "Verification Not Available";
                                }

                                JSONObject CheckInPoints = venuInsideJSONobject.getJSONObject("stats");

                                int checkinpoint = CheckInPoints.getInt("checkinsCount");
                                placeDetail.setCheckIn(checkinpoint);

                                db.addplaceDetails(new PlaceDetail(name, palceAddress, imagePath, verify,checkinpoint));

                                _placeList.add(placeDetail);

                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapterList.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {


                    showMessage("Volley Error" + volleyError.getMessage().toString());

                }
            });

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private void showMessage(String s) {

        Toast.makeText(PlacesAfterSearch.this, s, Toast.LENGTH_SHORT).show();
    }


}
