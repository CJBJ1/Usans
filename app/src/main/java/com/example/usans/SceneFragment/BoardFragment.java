package com.example.usans.SceneFragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;


public class BoardFragment extends Fragment {
    View view;
    ListView listView;
    TitleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board, container, false);

            listView = view.findViewById(R.id.board_list_view);

            adapter = new TitleAdapter();
            adapter.addItem(new TitleItem(0, "서재훈", "05/01 12:34", "여기 좋아요","굳굳"));
            adapter.addItem(new TitleItem(0, "ㅇㅎㅇ", "05/01 12:10", "운동 같이 하실분?","ㅇㅇ"));
            listView.setAdapter(adapter);

        return view;
    }

}
