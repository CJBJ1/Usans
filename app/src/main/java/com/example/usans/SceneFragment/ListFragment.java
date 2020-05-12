package com.example.usans.SceneFragment;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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
    FacilityAdapter adapter;
    FacilityList facilityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);


        sansListView = view.findViewById(R.id.sans_list_view);
        facilityList = (FacilityList) getActivity().getApplication();
        sortedList = new ArrayList<>();
        adapter = new FacilityAdapter();


        return view;
    }

    public void updateAdapter(){
        adapter = new FacilityAdapter();
            ArrayList<Facility> sortedList = facilityList.getArrayList();

            Comparator<Facility> comparator = new Comparator<Facility>() {
                @Override
                public int compare(Facility facility, Facility t1) {
                    return Double.compare(facility.getDistance(),t1.getDistance());
                }

            };

            Collections.sort(sortedList, comparator);

            for(int i=0;i<5;i++) {
                Facility sortedData = new Facility(sortedList.get(i));
               /* if(sortedData.getPhoto().length==0) {
                    sortedData.setPhoto(facilityList.getImageList());
                }*/
                adapter.addItem(sortedData);
            }
            sansListView.setAdapter(adapter);
    }
}
