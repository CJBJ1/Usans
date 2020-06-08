package com.example.usans.SceneFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.usans.MainActivity;
import com.example.usans.R;

import java.util.List;

public class RegFragment extends Fragment {
    View view;

    Button hotButton, bestButton, freeButton, recommendButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg, container, false);

        hotButton = view.findViewById(R.id.hot_button);
        bestButton = view.findViewById(R.id.best_button);
        freeButton = view.findViewById(R.id.free_button);
        recommendButton = view.findViewById(R.id.recommend_button);

        hotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoard(3);
            }
        });
        bestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoard(4);
            }
        });
        freeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoard(2);
            }
        });
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBoard(5);
            }
        });

        return view;
    }

    public void showBoard(int n) {
        MainActivity main = (MainActivity) getActivity();
        main.moveToBoard(n,0);
    }

}
