package com.example.usans.SceneFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usans.R;

public class RoutineDetailFragment extends Fragment {
    View view;
    String category = "";
    String routine = "";

    public RoutineDetailFragment(String category, String routine) {
        this.category = category;
        this.routine = routine;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_routine_detail, container, false);



        return view;
    }
}
