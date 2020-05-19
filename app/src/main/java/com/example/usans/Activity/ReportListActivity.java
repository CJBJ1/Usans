package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.usans.Adapter.CommentAdapter;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.Facility;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportListActivity extends AppCompatActivity {
    ListView reportListView;
    CommentAdapter adapter;

    Button reportButton;
    String machines;
    String facilityID;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        Intent intent = getIntent();
        machines = intent.getStringExtra("machines");
        facilityID = intent.getStringExtra("facilityID");
        userID = intent.getStringExtra("userID");

        reportListView = findViewById(R.id.report_list_view);
        reportButton = findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToReport();
            }
        });

        String url = "http://3.34.18.171.nip.io:8000/api/Review";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

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
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("서버", s);
            try {
                JSONArray jsArr = new JSONArray(s);
                int index = jsArr.length() - 1;

                adapter = new CommentAdapter();
                while (index != -1) {
                    JSONObject jsonObject = jsArr.getJSONObject(index);
                    if (jsonObject.getString("loc").equals(facilityID) && Float.parseFloat(jsonObject.getString("rating")) < 0) {
                        adapter.addItem(new CommentItem(jsonObject.getString("username"), 0, -1, jsonObject.getString("text"),  jsonObject.getString("machinestate")));
                    }
                    index--;
                }

                reportListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void moveToReport() {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("machines", machines);
        intent.putExtra("facilityID", facilityID);
        intent.putExtra("userID", userID);
        startActivityForResult(intent, 100);
    }

}
