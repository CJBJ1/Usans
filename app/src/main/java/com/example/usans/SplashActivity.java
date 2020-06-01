package com.example.usans;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import org.json.JSONArray;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.usans.Data.FacilityList;

public class SplashActivity extends AppCompatActivity {
    private FacilityList facilityList;
    private JSONArray jsArr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        facilityList = (FacilityList) getApplication();

        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}
