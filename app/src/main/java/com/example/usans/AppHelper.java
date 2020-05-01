package com.example.usans;

import com.android.volley.RequestQueue;

public class AppHelper {
    public static RequestQueue requestQueue;

    public static String host = "boostcourse-appapi.connect.or.kr";
    public static String readMovieList = "/movie/readMovieList";
    public static String readMovie = "/movie/readMovie";
    public static String readCommentList = "/movie/readCommentList";
    public static String createComment = "/movie/createComment";

    public static int port = 10000;
}