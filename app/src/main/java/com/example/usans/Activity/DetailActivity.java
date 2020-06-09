package com.example.usans.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.usans.Adapter.CommentAdapter;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.Facility;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.example.usans.Data.FacilityList;
import com.example.usans.RequestHttpURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.usans.MainActivity.LOGIN_IS_REQUIRED;

public class DetailActivity extends AppCompatActivity {
    Intent intent;
    Facility facility;
    FacilityList facilityList;

    Button writeCommentButton, reportButton;
    ListView commentListView;

    CommentAdapter adapter;

    ImageView imageView;
    TextView nameView, addressView, machinesView;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent = getIntent();
        facility = intent.getParcelableExtra("facility");
        facilityList = (FacilityList) getApplication();
        imageView = findViewById(R.id.sans_image_view);
        nameView = findViewById(R.id.sans_name);
        addressView = findViewById(R.id.sans_address);
        machinesView = findViewById(R.id.sans_machines);
        ratingBar = findViewById(R.id.sans_ratingBar);

        reportButton = findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToReport();
            }
        });
        writeCommentButton = findViewById(R.id.write_comment_button);
        writeCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToWrite();
            }
        });
        commentListView = findViewById(R.id.comment_list_view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToImage(facility.getPhoto());
            }
        });

        getSupportActionBar().setTitle("상세정보");
        String url = "http://3.34.18.171.nip.io:8000/api/Review";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
        layout();
    }

    public void moveToImage(String[] photos) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("photos", photos);
        startActivity(intent);
    }

    public void moveToReport() {
        if (facilityList.getUser()!=null) {
            Intent intent = new Intent(this, ReportListActivity.class);
            intent.putExtra("machines", facility.getMachines());
            intent.putExtra("facilityID", facility.getId());
            intent.putExtra("userID", facilityList.getUser().getId());
            startActivityForResult(intent, 100);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setTitle("로그인이 필요합니다.");
            builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent();
                    facilityList.setGoToComment(Integer.parseInt(facility.getId()));
                    setResult(10001,intent);
                    finish();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void moveToWrite() {
        if (facilityList.getUser()!=null) {
            Intent intent = new Intent(this, WriteCommentActivity.class);
            intent.putExtra("title",facility.getName());
            intent.putExtra("facilityID", facility.getId());
            intent.putExtra("userID", facilityList.getUser().getId());
            startActivityForResult(intent, 100);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setTitle("로그인이 필요합니다.");
            builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent();
                    facilityList.setGoToComment(Integer.parseInt(facility.getId()));
                    setResult(10001,intent);
                    finish();
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void layout() {
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        StringBuilder machines = new StringBuilder();
        for (String machine : facility.getMachines().split(" ")) {
            machines.append(machine + "\t   ");
        }
        machinesView.setText(machines.toString());
        ratingBar.setRating(facility.getRating());
        if (facility.getPhoto() != null) {
            if (facility.getPhoto().length != 0) {
                Glide.with(this).load(facility.getPhoto()[0]).into(imageView);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 101) {
                String url = "http://3.34.18.171.nip.io:8000/api/Review";
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
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
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsArr = new JSONArray(s);
                int index = jsArr.length() - 1;
                adapter = new CommentAdapter();
                while (index != -1) {
                    JSONObject jsonObject = jsArr.getJSONObject(index);
                    Float rating = Float.parseFloat(jsonObject.getString("rating"));
                    if (jsonObject.getString("loc").equals(facility.getId()) && rating > -1) {
                        adapter.addItem(new CommentItem(jsonObject.getString("username"), 0, rating, jsonObject.getString("text"),  ""/*jsonObject.getString("machinestate")*/));
                    }
                    index--;
                }

                commentListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

