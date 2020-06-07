package com.example.usans.CustomLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.MainActivity;
import com.example.usans.MountainPathDrawer;
import com.example.usans.R;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Info extends Fragment {
    private MainActivity main;
    private Facility facility;
    private FacilityList facilityList;
    private TMapView tMapView;
    private FragmentManager fm;

    View view;
    Button closeButton, detailButton, routineRecommendButton, startButton;

    ImageView imageView;
    ImageView imageView2;
    TextView nameView, addressView, machinesView;
    RatingBar ratingBar;

    public Info (Facility facility,TMapView tMapView) {
        this.facility = new Facility(facility);
        this.tMapView = tMapView;
    }

    public Info(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_view, container, false);
        facilityList = (FacilityList)getActivity().getApplication();
        fm = facilityList.getFm();

        main = (MainActivity) getActivity();
        imageView = view.findViewById(R.id.sans_image_view);
        imageView2 = (ImageView)view.findViewById(R.id.sans_sub_image_view);
        nameView = view.findViewById(R.id.sans_name);
        addressView = view.findViewById(R.id.sans_address);
        machinesView = view.findViewById(R.id.sans_machines);
        ratingBar = view.findViewById(R.id.sans_ratingBar);

        closeButton = view.findViewById(R.id.close_button);
        detailButton = view.findViewById(R.id.detail_button);
        routineRecommendButton = view.findViewById(R.id.routine_recommend_button);
        startButton = view.findViewById(R.id.sans_navigation_start_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info.super.getActivity().onBackPressed();
            }
        });
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.moveToDetail(facility);
            }
        });
        routineRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity act = (MainActivity) getActivity();
                act.moveToRoutine(facility.getMachines());
//                closeInfo();
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.setCenterPoint(126.952264 , 37.503149,true);
                main.onBackPressed();
                main.setSelectedFacility(facility);
                main.invalidRoute(0);
                //MountainPathDrawer mountainPathDrawer = new MountainPathDrawer(); 여깁니다
                //mountainPathDrawer.drawMountainPath(tMapView,new TMapPoint(Double.parseDouble(facility.getLat()), Double.parseDouble(facility.getLng())),getContext(),facilityList);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("imageView", "클릭됨");
                MainActivity main = (MainActivity) getActivity();
                main.moveToImage(facility.getPhoto());
            }
        });

        if (this.facility !=null) layout();
        return view;
    }

    public void closeInfo() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this);
        ft.commit();
    }

    public void layout() {
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        StringBuilder machines = new StringBuilder();
        if (facility.getMachines() != null) {
            for (String machine : facility.getMachines().split(" ")) {
                machines.append(machine + "\t   ");
            }
        }
        machinesView.setText(machines.toString());
        ratingBar.setRating(facility.getRating());
        if(facility.getPhoto().length!=0) {
            Glide.with(getContext()).load(facility.getPhoto()[0]).into(imageView);
        }
    }

}
