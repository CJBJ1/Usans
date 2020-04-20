package com.example.usans;

import android.content.ContentValues;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RequestHttpURLConnection {
    private String accessToken = null;

    public void setAccessToken(String token){
        this.accessToken = token;
    }
    public String request(String _url, ContentValues _params, int isPost) {

        HttpURLConnection urlConn = null;
        StringBuffer sbParams = new StringBuffer();

        if (_params == null)
            sbParams.append("");
        else {
            boolean isAnd = false;
            String key;
            String value;

            for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();

                if (isAnd)
                    sbParams.append("&");

                sbParams.append(key).append("=").append(value);

                if (!isAnd)
                    if (_params.size() >= 2)
                        isAnd = true;
            }
        }
        try {
            URL url = new URL(_url);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setReadTimeout(10000);
            urlConn.setConnectTimeout(15000);
            if(isPost ==1) {
                urlConn.setRequestMethod("POST");
            }
            else{
                urlConn.setRequestMethod("GET");
            }
            urlConn.setDoInput(true);
            urlConn.setRequestProperty("Accept-Charset", "utf-8");
            if(accessToken!=null) {
                Log.d("access",accessToken);
                urlConn.setRequestProperty("Authorization", "Token "+ accessToken);
            }
            urlConn.setRequestProperty("Content-Type", "application/json");

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

            String line;
            String page = "";

            while ((line = reader.readLine()) != null) {
                page += line;
            }
            return page;

        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }

}
