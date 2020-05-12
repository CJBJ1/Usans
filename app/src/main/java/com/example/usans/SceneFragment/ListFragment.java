package com.example.usans.SceneFragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usans.Activity.DetailActivity;
import com.example.usans.Adapter.CommentAdapter;
import com.example.usans.Adapter.FacilityAdapter;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.CommentList;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment {
    private LatLng userLocation;
    private ArrayList<Facility> sortedList;
    View view;
    ListView sansListView;
    Facility facility;
    FacilityList facilityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);


        sansListView = view.findViewById(R.id.sans_list_view);
        facilityList = (FacilityList) getActivity().getApplication();
        sortedList = new ArrayList<>();


        return view;
    }

    public void updateAdapter(){
        FacilityAdapter adapter = new FacilityAdapter();
        sortedList = new ArrayList<>(facilityList.getArrayList());

        adapter.addItem(new Facility(sortedList.get(1)));
        Log.d("겟겟",facilityList.getArrayList().get(1).getName());
        adapter.addItem(new Facility(sortedList.get(3)));
        adapter.addItem(new Facility(sortedList.get(2)));
        adapter.addItem(new Facility(sortedList.get(0)));

            Comparator<Facility> comparator = new Comparator<Facility>() {
                @Override
                public int compare(Facility facility, Facility t1) {
                    return Double.compare(facility.getDistance(),t1.getDistance());
                }

            };

            Collections.sort(sortedList, comparator);


        sansListView.setAdapter(adapter);
        sansListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(),DetailActivity.class);
                intent.putExtra("facility", (Facility) adapterView.getItemAtPosition(i));
                startActivity(intent);

            }
        });
    }
}
