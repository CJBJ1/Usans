package com.example.usans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.usans.CustomLayout.CustomActionBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button homeButton,listButton,regButton,mypageButton;
    FragmentTransaction tran;
    HomeFragment homeFragment;
    ListFragment listFragment;
    MypageFragment mypageFragment;
    RegFragment regFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

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
        setActionBar();
    }

    private void setActionBar() {
        CustomActionBar ca = new CustomActionBar(this, getSupportActionBar());
        ca.setActionBar();
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
    public void setFrag(int n){    //프래그먼트 교체 메소드
        switch (n){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, listFragment).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, regFragment).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mypageFragment).commit();
                break;
        }
    }
}
