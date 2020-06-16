package com.example.usans.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.usans.Data.TitleItem;
import com.example.usans.R;
import com.example.usans.SceneFragment.BoardDetailFragment;
import com.example.usans.SceneFragment.BoardFragment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardActivity extends AppCompatActivity {
    BoardFragment boardFragment;
    int goToComment;
    File tempSelectFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        int boardNumber = getIntent().getIntExtra("boardNumber", 0);
        if(boardNumber==3)
            getSupportActionBar().setTitle("HOT 게시판");
        else if(boardNumber==4)
            getSupportActionBar().setTitle("BEST 게시판");
        else if(boardNumber==2)
            getSupportActionBar().setTitle("자유 게시판");
        else if(boardNumber==5)
            getSupportActionBar().setTitle("추천 게시판");

        int userId = getIntent().getIntExtra("author", 0);

        if (boardNumber == -1) {
            BoardDetailFragment detail = new BoardDetailFragment(0, "","","내가 쓴 댓글","", -1);
            getSupportFragmentManager().beginTransaction().add(R.id.board_frameLayout, detail).commit();
        } else {
            if (userId != 0)
                boardFragment = new BoardFragment(userId);
            else {
                goToComment = getIntent().getIntExtra("goToComment", 0);
                boardFragment = new BoardFragment(boardNumber, goToComment);
            }
            getSupportFragmentManager().beginTransaction().add(R.id.board_frameLayout, boardFragment).commit();
        }
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
