package com.example.usans;

import android.content.ContentValues;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestHttpURLConnection {

    private String accessToken = null;
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String request(String _url, ContentValues _params) {
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

                if (isAnd) sbParams.append("&");
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
            urlConn.setRequestMethod("GET");
            urlConn.setDoInput(true);
            urlConn.setRequestProperty("Accept-Charset", "utf-8");
            if(accessToken!=null) {
                Log.d("access",accessToken);
                urlConn.setRequestProperty("Authorization", "Token "+ accessToken);
            }
            urlConn.setRequestProperty("Content-Type", "application/json");

            Log.d("응답코드",urlConn.getResponseCode() + "");
            Log.d("바디",urlConn.getResponseMessage());

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) return null;

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

    public String requestBody(String _url, ContentValues _params) {

        HttpURLConnection urlConn = null;
        JSONObject jsonParam = new JSONObject();
        // URL 뒤에 붙여서 보낼 파라미터.
        StringBuffer sbParams = new StringBuffer();

        // 보낼 데이터가 없으면 파라미터를 비운다.
        if (_params == null)
            sbParams.append("");
            // 보낼 데이터가 있으면 파라미터를 채운다.
        else {
            // 파라미터가 2개 이상이면 파라미터 연결에 &가 필요하므로 스위칭할 변수 생성.
            boolean isAnd = false;
            // 파라미터 키와 값.
            String key;
            String value;
            int index = 0;
            for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();
                index++;
                Log.d("key + value",index + " " + key + " " + value);
                try {
                    jsonParam.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

            // [2-1]. urlConn 설정.
            urlConn.setReadTimeout(10000);
            urlConn.setConnectTimeout(15000);
            urlConn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : GET/POST.
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setRequestProperty("Accept-Charset", "utf-8"); // Accept-Charset 설정.
            urlConn.setRequestProperty("Content-Type", "application/json");

            PrintWriter pw = new PrintWriter(new OutputStreamWriter(urlConn.getOutputStream()));
            pw.write(jsonParam.toString());
            pw.flush();
            pw.close();

            Log.d("응답코드",urlConn.getResponseCode() + "");
            Log.d("바디",urlConn.getResponseMessage());

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            String page = "";

            // 라인을 받아와 합친다.
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
