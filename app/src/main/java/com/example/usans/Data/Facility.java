package com.example.usans.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skt.Tmap.TMapMarkerItem2;

import java.util.ArrayList;

public class Facility implements Parcelable {

    private String id;
    private String name;
    private String address;
    private String[] photo;
    private String machines;
    private ArrayList<String> machineList;
    private String lat;
    private String lng;

    private float rating;
    private TMapMarkerItem2 marker;
    private double distance;

    public Facility(){}

    public Facility(String id, String name, String address, String[] photo, String machines, String lat, String lng, float rating,double distance){
        setId(id);
        setName(name);
        setAddress(address);
        setPhoto(photo);
        setMachines(machines);
        setLat(lat);
        setLng(lng);
        setRating(rating);
        setDistance(distance);
    }

    public Facility(Facility facility){
        setId(facility.getId());
        setName(facility.getName());
        setAddress(facility.getAddress());

        if(facility.getPhoto()==null) setPhoto(new String[]{});
        else setPhoto(facility.getPhoto());

        if(facility.getMachines()==null) setMachines("철봉 허리돌리기 평행봉");
        else {
                setMachines(facility.getMachines());
                machineList = new ArrayList<>();
                for (String machine : facility.getMachines().split(" "))
                    machineList.add(machine);
        }
        setLat(facility.getLat());
        setLng(facility.getLng());
        if(facility.getRating()==0) {
            setRating(Integer.parseInt(facility.getId()) % 3 + 2);
        }
        else{
            setRating(facility.getRating());
        }
        setDistance(facility.getDistance());
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

    public void setMarker(TMapMarkerItem2 marker) {
        this.marker = marker;
    }

    public TMapMarkerItem2 getMarker() {
        return marker;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setMachineList(ArrayList<String> machineList) {
        this.machineList = machineList;
    }

    public ArrayList<String> getMachineList() {
        return machineList;
    }
}

