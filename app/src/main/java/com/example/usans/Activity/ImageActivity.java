package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.usans.R;

public class ImageActivity extends AppCompatActivity {
    String[] photos;
    ImageView[] imageViews;
    Intent intent;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageViews = new ImageView[9];
        ImageView imageView1 = findViewById(R.id.imageView1);
        imageViews[0] = imageView1;
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageViews[1] = imageView2;
        ImageView imageView3 = findViewById(R.id.imageView3);
        imageViews[2] = imageView3;
        ImageView imageView4 = findViewById(R.id.imageView4);
        imageViews[3] = imageView4;
        ImageView imageView5 = findViewById(R.id.imageView5);
        imageViews[4] = imageView5;
        ImageView imageView6 = findViewById(R.id.imageView6);
        imageViews[5] = imageView6;
        ImageView imageView7 = findViewById(R.id.imageView7);
        imageViews[6] = imageView7;
        ImageView imageView8 = findViewById(R.id.imageView8);
        imageViews[7] = imageView8;
        ImageView imageView9 = findViewById(R.id.imageView9);
        imageViews[8] = imageView9;

        intent = getIntent();
        photos = intent.getStringArrayExtra("photos");

        getSupportActionBar().hide();

        setImage();
    }

    public void setImage() {
        for (int i=0; i<photos.length; i++) {
            Glide.with(getApplicationContext()).load(photos[i]).into(imageViews[i]);
            final int finalI = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("이미지 클릭", "확인");
                    imageUrl = photos[finalI];
                    moveToBig();
                }
            });
        }
    }

    public void moveToBig() {
        intent = new Intent(this, BigActivity.class);
        intent.putExtra("photo", imageUrl);
        startActivity(intent);
    }

}
