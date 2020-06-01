package com.example.usans.Data;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.URI;

public class BoardItem  implements Parcelable {
    public int id;
    public String writer;
    public String time;
    public String title;
    public String contents;
    public String imageURL;
    public URI uri;
    public int views;

    public BoardItem(int id, String writer, String time, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.time = time;
        this.title = title;
        this.contents = contents;
    }

    public BoardItem(Parcel src) {
        id = src.readInt();
        writer = src.readString();
        time = src.readString();
        title = src.readString();
        contents = src.readString();
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
        dest.writeString(writer);
        dest.writeString(time);
        dest.writeString(title);
        dest.writeString(contents);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
