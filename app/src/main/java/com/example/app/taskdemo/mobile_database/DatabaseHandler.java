package com.example.app.taskdemo.mobile_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app.taskdemo.places_utill.PlaceDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duke on 6/26/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABAE_NAME = "task";
    private static final String TABLE_PLACE_DETAILS = "place";

    // contets of the table
    private static final String KEY_PLACE_NAME = "palce_name";
    private static final String KEY_VERIFIED = "place_verification";
    private static final String KEY_IMAGE_PATH = "image";
    private static final String KEY_ADDRESS = "address";
    private static final String CHECKIN = "check_in";


    public DatabaseHandler(Context context) {
        super(context, DATABAE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACE_DETAILS + "("
                + KEY_PLACE_NAME + " VARCHAR(150), " + KEY_VERIFIED + " VARCHAR(20), " + KEY_IMAGE_PATH + " TEXT ," + KEY_ADDRESS + " TEXT, " + CHECKIN + " INT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE_DETAILS);
        onCreate(db);
        db.close();

    }


    public void addplaceDetails(PlaceDetail placeDetail) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, placeDetail.getPlaceName());
        values.put(KEY_VERIFIED, placeDetail.getPlaceAddress());
        values.put(KEY_IMAGE_PATH, placeDetail.getPlaceImageUrl());
        values.put(KEY_ADDRESS, placeDetail.getPlaceVerefiedOrNot());
        values.put(CHECKIN, placeDetail.get_checkIn());
        db.insert(TABLE_PLACE_DETAILS, null, values);
        db.close();
    }

    public void clearDataFromTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String dataClearCommand = "DELETE FROM " + TABLE_PLACE_DETAILS;
        db.execSQL(dataClearCommand);
        db.execSQL("vacuum");

    }


    public List<PlaceDetail> getAllPlaces() {

        List<PlaceDetail> placeDetails = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_PLACE_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                PlaceDetail placeDetail = new PlaceDetail();
                placeDetail.setPlaceName(cursor.getString(0));
                placeDetail.setPlaceAddress(cursor.getString(1));
                placeDetail.setPlaceImage(cursor.getString(2));
                placeDetail.setPlaceVerfication(cursor.getString(3));
                placeDetail.setCheckIn(cursor.getInt(4));
                placeDetails.add(placeDetail);
            } while (cursor.moveToNext());
        }

        return placeDetails;

    }


}
