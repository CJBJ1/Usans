package com.example.usans.CustomLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.usans.MainActivity;
import com.example.usans.R;

public class Info extends Fragment {
    View view;
    Button closeButton, detailButton, startButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_view, container, false);
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
                main.moveToDetail();
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

        return view;
    }
}
