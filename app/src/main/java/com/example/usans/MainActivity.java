package com.example.usans;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.usans.Activity.AddSansAcitivity;
import com.example.usans.Activity.BoardActivity;
import com.example.usans.Activity.DetailActivity;
import com.example.usans.CustomLayout.CustomActionBar;
import com.example.usans.Data.Facility;
import com.example.usans.SceneFragment.HomeFragment;
import com.example.usans.SceneFragment.ListFragment;
import com.example.usans.SceneFragment.MypageFragment;
import com.example.usans.SceneFragment.RegFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button homeButton,listButton,regButton,mypageButton;
    FragmentTransaction tran;

    public HomeFragment homeFragment;
    ListFragment listFragment;
    MypageFragment mypageFragment;
    RegFragment regFragment;

    public CustomActionBar ca;

    RelativeLayout homeLayout, listLayout, regLayout, mypageLayout, heartLayout;

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
        homeLayout = findViewById(R.id.homelayout);
        listLayout = findViewById(R.id.listlayout);
        regLayout = findViewById(R.id.reglayout);
        mypageLayout = findViewById(R.id.mypagelayout);
        heartLayout = findViewById(R.id.heartlayout);

        homeButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
        mypageButton.setOnClickListener(this);

        homeFragment = new HomeFragment();
        listFragment = new ListFragment();
        mypageFragment = new MypageFragment();
        regFragment = new RegFragment();

//        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, heartFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, mypageFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, regFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, homeFragment).commit();
        setBackground(0);
        ca.setActionBar(0);
    }

    public void moveToBoard() {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }

    public void moveToAddSans() {
        Intent intent = new Intent(this, AddSansAcitivity.class);
        startActivity(intent);
    }

    public void moveToDetail(Facility facility) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("facility", facility);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.homebutton:
                setBackground(0);
                setFrag(0);
                break;
            case R.id.listbutton:
                setBackground(1);
                setFrag(1);
                break;
            case R.id.regbutton:
                setBackground(2);
                setFrag(2);
                break;
            case R.id.mypagebutton:
                setBackground(3);
                setFrag(3);
                break;
        }
    }

    public void setBackground(int n) {
        switch (n) {
            case 0:
                homeLayout.setBackgroundColor(Color.LTGRAY);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 1:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.LTGRAY);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 2:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.LTGRAY);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 3:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.LTGRAY);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 4:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.LTGRAY);
                break;
        }
    }

    public void setFrag(int n) {    //프래그먼트 교체 메소드
        ca.setActionBar(n);
        switch (n) {
            case 0:
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(listFragment).commit();
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(regFragment).commit();
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().show(mypageFragment).commit();
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
