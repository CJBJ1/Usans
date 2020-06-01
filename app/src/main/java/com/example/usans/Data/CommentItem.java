package com.example.usans.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentItem implements Parcelable {
    public int id;
    public String writer;
    public int sansId;
    public String time;
    public float rating;
    public String contents;
    public String machine;

    public CommentItem(String writer, int sansId, float rating, String contents, String machine) {
        this.id = id;
        this.writer = writer;
        this.sansId = sansId;
        this.time = time;
        this.rating = rating;
        this.contents = contents;
        this.machine = machine;
    }

    public CommentItem(Parcel src) {
        id = src.readInt();
        writer = src.readString();
        sansId = src.readInt();
        time = src.readString();
        rating = src.readFloat();
        contents = src.readString();
        machine = src.readString();
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
        dest.writeInt(id);
        dest.writeString(writer);
        dest.writeInt(sansId);
        dest.writeString(time);
        dest.writeFloat(rating);
        dest.writeString(contents);
        dest.writeString(machine);
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

    public String getTime() {
        return time;
    }

    public float getRating() {
        return rating;
    }

    public String getContents() {
        return contents;
    }

    public String getMachine() {
        return machine;
    }
}
