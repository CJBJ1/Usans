package com.example.usans.Data;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.usans.GpsTracker;
import com.example.usans.R;

public class RoutineDetailItemView extends LinearLayout {
    int id;
    ImageView thumbnailView1;
    ImageView thumbnailView2;
    TextView titleView1;
    TextView titleView2;
    TextView nameView;

    public RoutineDetailItemView(Context context) {
        super(context);
        init(context);
    }

    public RoutineDetailItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.routine_detail_item_view, this, true);

        nameView = (TextView) findViewById(R.id.routine_detail_name);
        thumbnailView1 = (ImageView)findViewById(R.id.thumbnail1);
        thumbnailView2=(ImageView)findViewById(R.id.thumbnail2);
        titleView1=(TextView)findViewById(R.id.youtube_title_view1);
        titleView2=(TextView)findViewById(R.id.youtube_title_view2);

    }

    public void setItemId(int id) {id = id;}
    public void setItemName(String name) {nameView.setText(name);}
    public void setItemThumbnail1(String thumbnail1) {
        Glide.with(getContext()).load(thumbnail1).into(thumbnailView1);
    }

    public void setItemThumbnail2(String thumbnail2) {
        Glide.with(getContext()).load(thumbnail2).into(thumbnailView2);
    }
    public void setUrl1(final String url1){
        thumbnailView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                getContext().startActivity(myIntent);
            }
        });
    }
    public void setUrl2(final String url2){
        thumbnailView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                getContext().startActivity(myIntent);
            }
        });
    }

    public void setTitle1(String title1){
        titleView1.setText(title1);
    }
    public void setTitle2(String title2){
        titleView2.setText(title2);
    }
}
