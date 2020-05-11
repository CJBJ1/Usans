package com.example.usans.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Facility implements Parcelable {

    private String id;
    private String name;
    private String address;
    private String[] photo;
    private String machines;
    private String lat;
    private String lng;
    private float rating;
    private Marker marker;

    public Facility() {
    }

    public Facility(String id, String name, String address, String[] photo, String machines, String lat, String lng, float rating){
        setId(id);
        setName(name);
        setAddress(address);
        setPhoto(photo);
        setMachines(machines);
        setLat(lat);
        setLng(lng);
        setRating(rating);
    }
    public Facility(Facility facility){
        setId(facility.getId());
        setName(facility.getName());
        setAddress(facility.getAddress());
        setPhoto(new String[]{"image"});
        setMachines("철봉 허리돌리기 평행봉");
        setLat(facility.getLat());
        setLng(facility.getLng());
        setRating(3);
    }

    public Facility(Parcel src) {
        id = src.readString();
        name = src.readString();
        address = src.readString();
        photo = src.createStringArray();
        machines = src.readString();
        lat = src.readString();
        lng = src.readString();
        rating = src.readFloat();
//        marker;
    }

    public static final Creator CREATOR = new Creator() {
        public Facility createFromParcel(Parcel src) {
            return new Facility(src);
        }

        public Facility[] newArray(int size) {
            return new Facility[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeStringArray(photo);
        dest.writeString(machines);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeFloat(rating);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getPhoto() {
        return photo;
    }

    public void setPhoto(String[] photo) {
        this.photo = photo;
    }

    public String getMachines() {
        return machines;
    }

    public void setMachines(String machines) {
        this.machines = machines;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }
}

