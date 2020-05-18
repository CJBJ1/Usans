package com.example.usans.Data;

import android.app.Application;

import com.example.usans.Data.Facility;
import com.google.android.gms.maps.model.Polyline;
import com.skt.Tmap.TMapMarkerItem2;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class FacilityList extends Application {
    private String accessToken;
    private ArrayList<Facility> faArrayList = new ArrayList<>(100);
    private User user;
    private Polyline polyline;
    private TMapView tMapView;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public ArrayList<Facility> getArrayList() {
        return faArrayList;
    }
    public void setArrayList(ArrayList<Facility> faArrayList) {
        this.faArrayList = faArrayList;
    }
    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }
    public Polyline getPolyline() {
        return polyline;
    }
    public void removePolyline(){polyline=null;}
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void settMapView(TMapView tMapView) {
        this.tMapView = tMapView;
    }

    public TMapView gettMapView() {
        return tMapView;
    }
}
