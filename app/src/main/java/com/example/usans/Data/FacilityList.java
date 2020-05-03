package com.example.usans.Data;

import android.app.Application;

import com.example.usans.Data.Facility;

import java.util.ArrayList;

public class FacilityList extends Application {
    String accessToken;
    private ArrayList<Facility> faArrayList = new ArrayList<>(500);
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

}
