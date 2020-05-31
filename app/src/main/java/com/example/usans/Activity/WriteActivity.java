package com.example.usans.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usans.CustomLayout.CustomActionBar;
import com.example.usans.GpsTracker;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

public class WriteActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        imageView = findViewById(R.id.wa_image_view);
        imageView.setVisibility(View.INVISIBLE);
       ContentValues contentValues = new ContentValues();
        contentValues.put("title", "테스트");
        contentValues.put("board", "2");
        contentValues.put("author", "8");
        contentValues.put("text", "테스트");

        String url = "http://3.34.18.171.nip.io:8000/arti/write/";
        NetworkTask networkTask = new NetworkTask(url, contentValues);
        networkTask.execute();

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
                doTakeAlbumAction();
                return true;
            }

            case R.id.wa_add:{
                Intent intent = new Intent();
                setResult(222, intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write, menu);
        return true;
    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                imageView.setImageURI(mImageCaptureUri);
            }
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            Log.d("실행","실행");
            result = requestHttpURLConnection.requestBody(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("POST","POST");
        }
    }
}
