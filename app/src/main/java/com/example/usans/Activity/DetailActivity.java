package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.usans.Adapter.CommentAdapter;
import com.example.usans.AppHelper;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.CommentList;
import com.example.usans.Data.Facility;
import com.example.usans.R;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    Intent intent;
    Facility facility;

    Button writeCommentButton, reportButton;
    ListView commentListView;

    CommentAdapter adapter;
    CommentList commentList;

    ImageView imageView;
    TextView nameView, addressView, machinesView;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent = getIntent();
        facility = intent.getParcelableExtra("facility");

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

        adapter = new CommentAdapter();
        adapter.addItem(new CommentItem("jaehoon", 0, 3, "테스트"));
        commentListView.setAdapter(adapter);

        layout();
    }

    public void requestCommentList(final int movieId) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + AppHelper.readCommentList;
        url += "?" + "id=" + movieId;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,    //GET 방식은 요청 path가 필요
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    public void processResponse(String response) {
        Gson gson = new Gson();

//        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
//        if (info.code == 200) {
//            commentList = gson.fromJson(response, CommentList.class);
//
//            setCommentList();
//        }
    }

    public void setCommentList() {
        for (int i = 0; i < commentList.result.size(); i++) {
            CommentItem commentItem = commentList.result.get(i);
            adapter.addItem(commentItem);
        }
        adapter.notifyDataSetChanged();
    }

    public void moveToReport() {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra("machines", facility.getMachines());
        startActivity(intent);
    }

    public void moveToWrite() {
        Intent intent = new Intent(this, WriteCommentActivity.class);
        startActivity(intent);
    }

    public void layout() {
//        imageView = facility.getPhoto();
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        machinesView.setText(facility.getMachines());
        ratingBar.setRating(facility.getRating());
    }
}
