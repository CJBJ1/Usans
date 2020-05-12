package com.example.usans.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.bumptech.glide.Glide;
import com.example.usans.Adapter.CommentAdapter;
import com.example.usans.AppHelper;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.CommentList;
import com.example.usans.Data.Facility;
import com.example.usans.R;
import com.example.usans.Data.FacilityList;
import com.google.gson.Gson;

import java.util.Random;

public class DetailActivity extends AppCompatActivity {
    Intent intent;
    Facility facility;
    FacilityList facilityList;

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
        facilityList = (FacilityList)getApplication();
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
        adapter.addItem(new CommentItem("jaehoon", 0, 3, "테스트 / 여기 기구들 전부 상태 좋고 야경이 너무 좋아요!!", ""));
        adapter.addItem(new CommentItem("beomjoon", 0, -1, "테스트 / 철봉이 녹이 많이 슬었어요", "철봉"));
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
        if(1==1) {
            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("machines", facility.getMachines());
            startActivity(intent);
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setTitle("로그인이 필요합니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void moveToWrite() {
        if(1==1) {
            Intent intent = new Intent(this, WriteCommentActivity.class);
            startActivity(intent);
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setTitle("로그인이 필요합니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void layout() {
//        imageView = facility.getPhoto();
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        machinesView.setText(facility.getMachines());
        ratingBar.setRating(facility.getRating());
        if(facility.getPhoto()!=null) {
            if (facility.getPhoto().length != 0) {
                Random rnd = new Random();
                int num = rnd.nextInt(facility.getPhoto().length);
                Glide.with(this).load(facility.getPhoto()[num]).into(imageView);
            }
        }
    }
}
