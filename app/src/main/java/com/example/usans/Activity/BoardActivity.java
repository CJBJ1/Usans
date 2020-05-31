package com.example.usans.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;
import com.example.usans.SceneFragment.BoardFragment;
import com.example.usans.SceneFragment.HomeFragment;
import com.example.usans.SceneFragment.ListFragment;
import com.example.usans.SceneFragment.MypageFragment;
import com.example.usans.SceneFragment.RegFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BoardActivity extends AppCompatActivity {
    private Button writeButton;
    FragmentTransaction tran;
    BoardFragment boardFragment;
    File tempSelectFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        boardFragment = new BoardFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.board_frameLayout, boardFragment).commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case 222 :{
                try {
                    String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
                    tempSelectFile = new File(Environment.getExternalStorageDirectory()+"/Pictures/Test/", "temp_" + date + ".jpeg");
                    OutputStream out = null;
                    out = new FileOutputStream(tempSelectFile);
                    //image.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
