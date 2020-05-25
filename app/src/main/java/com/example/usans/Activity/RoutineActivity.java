package com.example.usans.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.usans.R;
import com.example.usans.SceneFragment.RoutineFragment;

public class RoutineActivity extends AppCompatActivity {
    RoutineFragment routineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        routineFragment = new RoutineFragment(getIntent().getStringExtra("machines"));
        getSupportFragmentManager().beginTransaction().add(R.id.routine_frame, routineFragment).commit();
    }
}
