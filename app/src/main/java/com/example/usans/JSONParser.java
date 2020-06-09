package com.example.usans;

import android.util.Log;

import com.example.usans.Data.FacilityList;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JSONParser {

    public static JSONObject uploadImage(String imageUploadUrl, String sourceImageFile,FacilityList facilityList) {
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
        try {
            File sourceFile = new File(sourceImageFile);
            Log.d("파일", "File...::::" + sourceFile + " : " + sourceFile.exists());
            String filename = sourceImageFile.substring(sourceImageFile.lastIndexOf("/")+1);

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    //.addFormDataPart("result", "photo_image")
                    //.addFormDataPart("authorname", facilityList.getUser().getName())
                    //.addFormDataPart("title", "test")
                    //.addFormDataPart("board", "2")
                    //.addFormDataPart("author", facilityList.getUser().getId())
                    //.addFormDataPart("text", "이미지")
                    .build();

            Request request = new Request.Builder()
                    .url(imageUploadUrl)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            if (response != null) {//
                if (response.isSuccessful()) {
                    String res = response.body().string();
                    Log.e("에러", "Error: " + res);
                    return new JSONObject(res);
                }
            }
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("에러", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("에러", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }

}
