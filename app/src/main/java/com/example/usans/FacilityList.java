package com.example.usans;

import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

public class FacilityList {
    private ArrayList<Facility> faArrayList = new ArrayList<>(500);
    public ArrayList<Facility> getArrayList() {
        return faArrayList;
    }
    public void setArrayList(ArrayList<Facility> faArrayList) {
        this.faArrayList = faArrayList;
    }

}
