package com.example.usans.Activity;

import android.os.Bundle;
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

public class BoardActivity extends AppCompatActivity {
    FragmentTransaction tran;
    BoardFragment boardFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        boardFragment = new BoardFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.board_frameLayout, boardFragment).commit();
    }
}
