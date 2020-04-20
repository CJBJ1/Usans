package com.example.usans;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.usans.CustomLayout.CustomActionBar;
import com.example.usans.SceneFragment.HomeFragment;
import com.example.usans.SceneFragment.ListFragment;
import com.example.usans.SceneFragment.MypageFragment;
import com.example.usans.SceneFragment.RegFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button homeButton,listButton,regButton,mypageButton;
    FragmentTransaction tran;

    Fragment homeFragment;
    Fragment listFragment;
    Fragment mypageFragment;
    Fragment regFragment;

    CustomActionBar ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        ca = new CustomActionBar(this, getSupportActionBar());

        homeButton = (Button)findViewById(R.id.homebutton);
        listButton = (Button)findViewById(R.id.listbutton);
        regButton = (Button)findViewById(R.id.regbutton);
        mypageButton = (Button)findViewById(R.id.mypagebutton);

        homeButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
        mypageButton.setOnClickListener(this);

        homeFragment = new HomeFragment();
        listFragment = new ListFragment();
        mypageFragment = new MypageFragment();
        regFragment = new RegFragment();

        setFrag(0); //프래그먼트 교체
        ca.setActionBar(0);
    }

    public void moveToDetail() {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.homebutton:
                setFrag(0);
                break;
            case R.id.listbutton:
                setFrag(1);
                break;
            case R.id.regbutton:
                setFrag(2);
                break;
            case R.id.mypagebutton:
                setFrag(3);
                break;
        }
    }
    public void setFrag(int n) {    //프래그먼트 교체 메소드
        Fragment curFragment = new Fragment();
        switch (n) {
            case 0:
                curFragment = homeFragment;
                break;
            case 1:
                curFragment = listFragment;
                break;
            case 2:
                curFragment = regFragment;
                break;
            case 3:
                curFragment = mypageFragment;
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, curFragment).commit();
        ca.setActionBar(n);
    }
}
