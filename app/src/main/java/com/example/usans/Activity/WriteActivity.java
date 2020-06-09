package com.example.usans.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usans.AppHelper;
import com.example.usans.Data.FacilityList;
import com.example.usans.JSONParser;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WriteActivity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private FacilityList facilityList;
    private Uri mImageCaptureUri;
    private ImageView imageView;
    EditText waTitle, waContent;
    ContentValues contentValues;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        facilityList = (FacilityList)getApplication();
        imageView = findViewById(R.id.wa_image_view);
        imageView.setVisibility(View.INVISIBLE);
        waTitle = findViewById(R.id.wa_title);
        waContent = findViewById(R.id.wa_content);
        contentValues = new ContentValues();

        Toolbar toolbar = findViewById(R.id.toolbar_write);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("글쓰기");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                onBackPressed();
                return true;
            }
            case R.id.wa_gallery:{
                getGallery();
                return true;
            }
            case R.id.wa_add:{
                Log.i("추가", "글쓰기 추가");
//                contentValues.put("authorname", facilityList.getUser().getName());
                contentValues.put("title", waTitle.getText().toString());
                contentValues.put("board", getIntent().getIntExtra("boardNumber", 0));
                contentValues.put("author", facilityList.getUser().getId());
                contentValues.put("text", waContent.getText().toString());
                NetworkTask networkTask = new NetworkTask(AppHelper.Write, contentValues);
                networkTask.execute();

                Intent intent = new Intent();
                setResult(22, null);
                //new ImageUploadTask().execute(AppHelper.Write, imagePath);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write, menu);
        return true;
    }

    private void getGallery() {
        // File System.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
        startActivityForResult(chooserIntent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(mImageCaptureUri);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(mImageCaptureUri, filePathColumn,
                        null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    imagePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));

                }
            }
        }
    }
    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;
        ProgressDialog progressDialog; // API 26에서 deprecated

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(WriteActivity.this);
            progressDialog.setMessage("게시글 업로드중....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.requestBody(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null)
                progressDialog.dismiss();

            finish();
        }
    }

    private  class ImageUploadTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog progressDialog; // API 26에서 deprecated

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(WriteActivity.this);
            progressDialog.setMessage("이미지 업로드");
            progressDialog.show();
        }
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                JSONObject jsonObject = JSONParser.uploadImage(params[0],params[1],facilityList);
                if (jsonObject != null){
                    return jsonObject.getString("result").equals("success");
                }
            } catch (JSONException e) {
               Log.i("TAG", "Error : " + e.getLocalizedMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (progressDialog != null)
                progressDialog.dismiss();

            if (aBoolean){
                Toast.makeText(getApplicationContext(), "파일 업로드 성공", Toast.LENGTH_LONG).show();
            }  else{
                Toast.makeText(getApplicationContext(), "파일 업로드 실패", Toast.LENGTH_LONG).show();
            }

            if(mImageCaptureUri != null){
                File file = new File(mImageCaptureUri.getPath());
                if(file.exists()) {
                    file.delete();
                }
                mImageCaptureUri = null;
            }
            imagePath = "";
            finish();
        }
    }
}
