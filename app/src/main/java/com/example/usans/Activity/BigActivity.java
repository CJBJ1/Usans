package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.usans.R;

public class BigActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big);

//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setCustomView(LayoutInflater.from(this).inflate(R.layout.custom_action_bar, null));
        getSupportActionBar().hide();

        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("photo");
        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
    }
}
