package com.example.usans.CustomLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.usans.R;

public class Recommend extends Fragment {
    View view;
    Button recommendButton, closeButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recommend_view, container, false);
        recommendButton = view.findViewById(R.id.sans_machines);
        closeButton = view.findViewById(R.id.close_button);

        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //루틴 추천 목록으로 이동

                closeRecommend();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeRecommend();
            }
        });

        return view;
    }

    public void closeRecommend() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this);
        ft.commit();
    }

}
