package com.example.usans;

import android.app.Application;

import android.app.Application;

import java.util.ArrayList;

public class FacilityList extends Application {
    private ArrayList<Facility> faArrayList = new ArrayList<>(500);
    public ArrayList<Facility> getArrayList() {
        return faArrayList;
    }
    public void setArrayList(ArrayList<Facility> faArrayList) {
        this.faArrayList = faArrayList;
    }

}
