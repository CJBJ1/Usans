package com.example.usans.Data;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.URI;

public class RoutineDetailItem  implements Parcelable {
    public int id;
    private String name;
    private String title1;
    private String title2;
    private String thumbnail1;
    private String thumbnail2;
    private String url1;
    private String url2;

    public RoutineDetailItem(int id, String name, String thumbnail1, String title1, String url1,String thumbnail2,
                             String title2, String url2) {
        this.id = id;
        this.name = name;
        this.thumbnail1 = thumbnail1;
        this.title1 = title1;
        this.url1 = url1;
        this.thumbnail2 = thumbnail2;
        this.title2 = title2;
        this.url2 = url2;
    }

    public RoutineDetailItem(Parcel src) {
        id = src.readInt();
        name = src.readString();
        thumbnail1 = src.readString();
        title1 = src.readString();
        url1 = src.readString();
        thumbnail2 = src.readString();
        title2 = src.readString();
        url2 = src.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public BoardItem createFromParcel(Parcel src) {
            return new BoardItem(src);
        }
        public BoardItem[] newArray(int size) {
            return new BoardItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(thumbnail1);
        dest.writeString(title1);
        dest.writeString(url1);
        dest.writeString(thumbnail2);
        dest.writeString(title2);
        dest.writeString(url2);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle1() {
        return title1;
    }



    public String getThumbnail2() {
        return thumbnail2;
    }

    public void setThumbnail2(String thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }

    public String getThumbnail1() {
        return thumbnail1;
    }

    public void setThumbnail1(String thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

}
