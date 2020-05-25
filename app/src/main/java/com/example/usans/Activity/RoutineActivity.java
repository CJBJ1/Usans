package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.usans.Adapter.RoutineAdapter;
import com.example.usans.Data.RoutineItem;
import com.example.usans.R;

public class RoutineActivity extends AppCompatActivity {
    ListView listView;
    RoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        listView = findViewById(R.id.listview);
        adapter = new RoutineAdapter();
        adapter.addItem(new RoutineItem(0, "가슴", "벤치프레스 -> 덤벨프레스 -> 딥스 -> 덤벨플라이"));
        adapter.addItem(new RoutineItem(0, "등", "풀업 -> 랫풀다운 -> 바벨로우"));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RoutineItem data = (RoutineItem) adapterView.getItemAtPosition(i);
                moveToDetailRoutine(data);
            }
        });
    }

    public void moveToDetailRoutine(RoutineItem data) {
    }

}
