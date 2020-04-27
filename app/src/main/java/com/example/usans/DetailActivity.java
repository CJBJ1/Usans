package com.example.usans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailActivity extends AppCompatActivity {
    Button writeCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        writeCommentButton = findViewById(R.id.write_comment_button);
        writeCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToWrite();
            }
        });

    }

    public void moveToWrite() {
        Intent intent = new Intent(this, WriteCommentActivity.class);
        startActivity(intent);
    }
}
