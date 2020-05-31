package com.example.usans.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usans.AppHelper;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WriteActivity extends AppCompatActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView imageView;
    EditText waTitle, waContent;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

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
                doTakeAlbumAction();
                return true;
            }
            case R.id.wa_add:{
                contentValues.put("title", waTitle.getText().toString());
                contentValues.put("board", getIntent().getIntExtra("boardNumber", 0));
                contentValues.put("author", "8");
                contentValues.put("text", waContent.getText().toString());
                NetworkTask networkTask = new NetworkTask(AppHelper.Write, contentValues);
                networkTask.execute();

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
            result = requestHttpURLConnection.requestBody(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
