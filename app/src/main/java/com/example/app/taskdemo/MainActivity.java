package com.example.app.taskdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.taskdemo.app_controler.InternetConnectionDetector;

public class MainActivity extends AppCompatActivity {


    public static final String FOUR_SQUARE_API = "";

    InternetConnectionDetector connectionDetector;

    private EditText inputData;
    String inputStringQuery = "";
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connectionDetector = new InternetConnectionDetector(MainActivity.this);

//        if (!connectionDetector.isConnectingToInternet()) {
//
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Internet Connection not available.\n Please Connect to Internet")
//                    .setCancelable(false)
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            finish();
//                        }
//                    });
//
//            AlertDialog alert = builder.create();
//            alert.show();
//        }


        inputData = (EditText) findViewById(R.id.editSearch_input);
        searchButton = (Button) findViewById(R.id.serch_task);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputStringQuery = inputData.getText().toString();
                if (inputStringQuery.length() <= 0) {
                    inputData.setError("Please Input Query !");
                } else {
                    Intent intent = new Intent(MainActivity.this, PlacesAfterSearch.class);
                    intent.putExtra("query", inputStringQuery);
                    startActivity(intent);
                    finish();
                }


            }
        });


    }

}
