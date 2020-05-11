package com.example.usans.Activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;

public class BoardActivity extends AppCompatActivity {
    ListView listView;
    TitleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        listView = findViewById(R.id.title_list_view);

        adapter = new TitleAdapter();
        adapter.addItem(new TitleItem(0, "서재훈", "05/01 12:34", "여기 좋아요","굳굳"));
        adapter.addItem(new TitleItem(0, "ㅇㅎㅇ", "05/01 12:10", "운동 같이 하실분?","ㅇㅇ"));
        listView.setAdapter(adapter);
    }
}
