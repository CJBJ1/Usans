package com.example.usans.SceneFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usans.Adapter.CommentAdapter;
import com.example.usans.Adapter.FacilityAdapter;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.CommentList;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.R;

public class ListFragment extends Fragment {
    View view;
    ListView sansListView;

    FacilityAdapter adapter;
    FacilityList facilityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        sansListView = view.findViewById(R.id.sans_list_view);
        adapter = new FacilityAdapter();

        adapter.addItem(new Facility("1","고구동산", "서울시 동작구 상도 1동 325-9", new String[]{"image url"}, "허리돌리기 철봉 평행봉", "0.0", "0.0", 3));

        sansListView.setAdapter(adapter);

        return view;
    }
}
