package com.example.usans.SceneFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usans.Adapter.BoardAdapter;
import com.example.usans.Adapter.CommentAdapter;
import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.Data.BoardItem;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;

import java.util.List;

public class RegFragment extends Fragment {
    View view;
    ListView listView;
    TitleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg, container, false);

        listView = (ListView)view.findViewById(R.id.title_list_view);

        adapter = new TitleAdapter();
        adapter.addItem(new TitleItem(0, "서재훈", "05/01 12:34", "여기 좋아요","굳굳"));
        listView.setAdapter(adapter);
        return view;
    }
}
