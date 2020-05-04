package com.example.usans.CustomLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usans.Data.Facility;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.google.android.gms.maps.model.LatLng;

public class Info extends Fragment {

    private Facility facility;

    View view;
    Button closeButton, detailButton, startButton;

    ImageView imageView;
    TextView nameView, addressView, machinesView;
    RatingBar ratingBar;


    public Info (Facility facility) {
        this.facility = facility;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_view, container, false);
        imageView = view.findViewById(R.id.sans_image_view);
        nameView = view.findViewById(R.id.sans_name);
        addressView = view.findViewById(R.id.sans_address);
        machinesView = view.findViewById(R.id.sans_machines);
        ratingBar = view.findViewById(R.id.sans_ratingBar);

        closeButton = view.findViewById(R.id.close_button);
        detailButton = view.findViewById(R.id.detail_button);
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
                MainActivity main = (MainActivity) Info.super.getActivity();
                main.moveToDetail(facility);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.super.getActivity().onBackPressed();

                MainActivity main = (MainActivity) Info.super.getActivity();
                main.ca.setActionBar(5);

                //길찾기 폴리곤 그리기
                //구글 핏 시작
            }
        });

        layout();
        return view;
    }

    public void layout() {
//        imageView = facility.getPhoto();
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        machinesView.setText(facility.getMachines());
        ratingBar.setRating(facility.getRating());
    }

}
