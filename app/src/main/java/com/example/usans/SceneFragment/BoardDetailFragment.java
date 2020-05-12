package com.example.usans.SceneFragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.usans.Activity.DetailActivity;
import com.example.usans.Adapter.BoardAdapter;
import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.CustomLayout.Info;
import com.example.usans.Data.BoardItem;
import com.example.usans.Data.Facility;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;


public class BoardDetailFragment extends Fragment {
    View view;
    int id;
    ImageView writerImageView;
    TextView writerView;
    TextView timeView;
    TextView titleView;
    TextView contentsView;

    String userId;
    String passTime;
    String title;
    String contents;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board_detail, container, false);

        writerImageView = (ImageView) view.findViewById(R.id.board_writer_image_view);
        writerView = (TextView) view.findViewById(R.id.board_writer);
        timeView = (TextView) view.findViewById(R.id.board_time);
        titleView = (TextView) view.findViewById(R.id.board_title);
        contentsView = (TextView) view.findViewById(R.id.board_contents);

        writerImageView.setImageResource(R.drawable.user1);
        writerView.setText(userId);
        timeView.setText(passTime);
        titleView.setText(title);
        contentsView.setText(contents);

        return view;
    }

    public BoardDetailFragment(String userId,String passTime,String title,String contents){
        this.userId = userId;
        this.passTime = passTime;
        this.title = title;
        this.contents = contents;
    }


}
