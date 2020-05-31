package com.example.usans.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class RouteItem implements Parcelable {
    public String route;

    public RouteItem(String route) {
        this.route = route;
    }

    public RouteItem(Parcel src) {
        route = src.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public CommentItem createFromParcel(Parcel src) {
            return new CommentItem(src);
        }

        public CommentItem[] newArray(int size) {
            return new CommentItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(route);
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}