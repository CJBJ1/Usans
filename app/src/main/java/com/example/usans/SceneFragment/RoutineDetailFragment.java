package com.example.usans.SceneFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.usans.R;
import java.util.ArrayList;
import java.util.List;

public class RoutineDetailFragment extends Fragment {
    View view;
    ListView listView;
    String category = "";
    String routine = "";

    public RoutineDetailFragment(String category, String routine) {
        this.category = category;
        this.routine = routine;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_routine_detail, container, false);

        TextView categoryView = view.findViewById(R.id.category_view);
        listView = view.findViewById(R.id.listView);
        List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        Button routineStart = view.findViewById(R.id.routine_start);
        categoryView.setText(this.category);

        for (String exercise : routine.split(" -> "))
            list.add(exercise);

        return view;
    }
}



