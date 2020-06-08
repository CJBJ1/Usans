package com.example.usans.Data;

public class TitleItem  {
    public int id;
    public String writer;
    public String time;
    public String title;
    public String contents;
    public int views;

    public TitleItem(int id, String writer, String time, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.time = time;
        this.title = title;
        this.contents = contents;
    }

    public TitleItem(String writer, String contents) {
        this.writer = writer;
        this.contents = contents;
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
