package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.usans.R;

public class BigActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big);

        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("photo");
        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
    }
}
