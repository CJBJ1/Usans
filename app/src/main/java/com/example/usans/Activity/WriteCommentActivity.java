package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usans.Data.Facility;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.example.usans.SceneFragment.HomeFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class WriteCommentActivity extends AppCompatActivity {
    Button saveButton, cancelButton;
    EditText contentsInput;
    TextView titleView;
    RatingBar ratingBar;
    String title;
    String userID;
    String facilityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);


        Intent intent = getIntent();
        saveButton = findViewById(R.id.save_button);
        cancelButton = findViewById(R.id.cancel_button);
        contentsInput = findViewById(R.id.contents_input);
        ratingBar = findViewById(R.id.write_rating_bar);
        titleView = findViewById(R.id.write_title_view);
        title = intent.getStringExtra("title");
        userID = intent.getStringExtra("userID");
        facilityID = intent.getStringExtra("facilityID");

        titleView.setText(title);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save()) onBackPressed();
                else Toast.makeText(getApplicationContext(), "내용을 입력해 주세요", Toast.LENGTH_SHORT).show();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("점수",ratingBar.getRating()+ "");
                onBackPressed();
            }
        });
    }

    public boolean save() {
        String contents = contentsInput.getText().toString();
        Intent intent = new Intent();
        if (contents.length() == 0) {
            setResult(200, intent);
            finish();
            return false;
        } else {
            Log.d("userID",userID);
            String url = "http://3.34.18.171.nip.io:8000/review/?user="+userID+"&loc="+facilityID+"&rating="+Math.round(ratingBar.getRating())+"&text="+contents;
            NetworkTask networkTask = new NetworkTask(url, null);
            networkTask.execute();
            setResult(101, intent);
            finish();
            return true;
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
            result = requestHttpURLConnection.request(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("POST","POST");
        }
    }

}
