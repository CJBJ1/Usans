package com.example.usans.SceneFragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.usans.Activity.DetailActivity;
import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.CustomLayout.Info;
import com.example.usans.Data.Facility;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;


public class BoardFragment extends Fragment {
    View view;
    ListView listView;
    TitleAdapter adapter;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board, container, false);

            listView = view.findViewById(R.id.board_list_view);
            fm = getFragmentManager();
            adapter = new TitleAdapter();
            adapter.addItem(new TitleItem(0, "서재훈", "05/01 12:34", "여기 좋아요","굳굳"));
            adapter.addItem(new TitleItem(0, "정재형", "05/01 12:10", "운동 같이 하실분?","컴컴"));
            listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fm.popBackStack();
                TitleItem data = (TitleItem) adapterView.getItemAtPosition(i);
                BoardDetailFragment detail = new BoardDetailFragment(data.getWriter(),data.getTime(),data.getTitle(),data.getContents());

                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.enter_to_left,R.anim.enter_from_left,R.anim.enter_to_right);
                transaction.replace(R.id.board_frameLayout, detail);
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });

        return view;
    }

}
