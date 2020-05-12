package com.example.usans.Data;

import android.app.Application;

import com.example.usans.Data.Facility;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

public class FacilityList extends Application {
    private String accessToken;
    private ArrayList<Facility> faArrayList = new ArrayList<>(100);
    private String[] imageList = new String[]{"http://www.etoland.co.kr/data/file0207/etoboard/0_nBFgHTpt_20171003_173830-800x600.jpg"
            ,"https://scontent-iad3-1.cdninstagram.com/v/t51.2885-15/sh0.08/e35/c125.0.750.750a/s640x640/91120737_827462051091233_6340051310469766177_n.jpg?_nc_ht=scontent-iad3-1.cdninstagram.com&_nc_cat=105&_nc_ohc=WT4MwMPHMXEAX_glgkL&oh=9913e721d448f7432ea4d8f90b4e2209&oe=5ECC6846",
            "https://www.ygosu.com/community/board/download.yg?idx=2731335","https://scontent-yyz1-1.cdninstagram.com/v/t51.2885-15/sh0.08/e35/s640x640/94355439_540921516614188_4546313830909369354_n.jpg?_nc_ht=scontent-yyz1-1.cdninstagram.com&_nc_cat=104&_nc_ohc=GrrcTcNUFE8AX_Rpz1f&oh=768ba0dfb997c68ecbf853a414ce96a2&oe=5ECF2475",
    "https://file3.instiz.net/data/file3/2020/01/25/c/d/4/cd483119fe76d7fe47a25512fd7dec56.jpg","https://scontent-lga3-1.cdninstagram.com/v/t51.2885-15/sh0.08/e35/s640x640/67845080_831964650530706_2443419457289453779_n.jpg?_nc_ht=scontent-lga3-1.cdninstagram.com&_nc_cat=106&_nc_ohc=iD_IlqtLPMEAX9f9GK5&oh=6c1a07957d322c987141306bae42d6cb&oe=5EDA14BA"};
    private User user;
    private Polyline polyline;

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

    public String[] getImageList() {
        return imageList;
    }
}
