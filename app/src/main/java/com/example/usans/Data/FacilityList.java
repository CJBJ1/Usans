package com.example.usans.Data;

import android.app.Application;
import com.example.usans.CustomLayout.Info;
import com.example.usans.Data.Facility;
import com.example.usans.MainActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import java.util.ArrayList;
import androidx.fragment.app.FragmentManager;

public class FacilityList extends Application {
    private String accessToken;
    private MainActivity mainActivity;
    private ArrayList<Facility> faArrayList = new ArrayList<>(100);
    private ArrayList<Facility> mountainList = new ArrayList<>(10);
    private User user;
    private LatLng userLocation;
    private TMapPolyLine polyline;
    private TMapView tMapView;
    private FragmentManager fm;
    private Info selectedInfo;
    private int isList = 0;
    private int goToComment = -1;
    private int goToBoard = -1;
    private int goToBoardComment = -1;
    private TitleItem goToTitleItem;

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
    public void setPolyline(TMapPolyLine polyline) {
        this.polyline = polyline;
    }
    public TMapPolyLine getPolyline() {
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
    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }
    public FragmentManager getFm() {
        return fm;
    }

    public ArrayList<Facility> getMountainList() {
        return mountainList;
    }

    public void setMountainList(ArrayList<Facility> mountainList) {
        this.mountainList = mountainList;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setGoToBoard(int goToBoard) {
        this.goToBoard = goToBoard;
    }

    public int getGoToBoard() {
        return goToBoard;
    }

    public void setGoToComment(int goToComment) {
        this.goToComment = goToComment;
    }

    public int getGoToComment() {
        return goToComment;
    }

    public void setGoToBoardComment(int goToBoardComment) {
        this.goToBoardComment = goToBoardComment;
    }

    public int getGoToBoardComment() {
        return goToBoardComment;
    }

    public void setGoToTitleItem(TitleItem goToTitleItem) {
        this.goToTitleItem = goToTitleItem;
    }

    public TitleItem getGoToTitleItem() {
        return goToTitleItem;
    }

    public int getIsList() {
        return isList;
    }

    public void setIsList(int isList) {
        this.isList = isList;
    }

    public void setUserLocation(LatLng userLocation) {
        this.userLocation = userLocation;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }
}
