package com.example.app.taskdemo.places_utill;

/**
 * Created by Duke on 12/29/2015.
 */
public class PlaceDetail {

    String _placeName, _placeImage, _placeVerifiedOrNot, _placeAddress;

    public PlaceDetail() {

    }

    public PlaceDetail(String placeName, String address, String imagepath, String verify) {

        this._placeName = placeName;
        this._placeAddress = address;
        this._placeVerifiedOrNot = verify;
        this._placeImage = imagepath;
    }

    public void setPlaceName(String placeName) {

        this._placeName = placeName;
    }

    public String getPlaceName() {
        return this._placeName;
    }

    public void setPlaceImage(String placeImage) {
        this._placeImage = placeImage;
    }

    public String getPlaceImageUrl() {
        return this._placeImage;
    }

    public void setPlaceVerfication(String verifedOrNot) {

        this._placeVerifiedOrNot = verifedOrNot;
    }

    public String getPlaceVerefiedOrNot() {
        return this._placeVerifiedOrNot;
    }

    public void setPlaceAddress(String placeAddress) {

        this._placeAddress = placeAddress;
    }

    public String getPlaceAddress() {
        return this._placeAddress;
    }
}
