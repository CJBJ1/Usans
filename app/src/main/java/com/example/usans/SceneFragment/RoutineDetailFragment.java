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

import com.example.usans.Adapter.RoutineAdapter;
import com.example.usans.Adapter.RoutineDetailAdapter;
import com.example.usans.Data.HowToMachine;
import com.example.usans.Data.RoutineDetailItem;
import com.example.usans.R;
import java.util.ArrayList;
import java.util.List;

public class RoutineDetailFragment extends Fragment {
    View view;
    ListView listView;
    String category = "";
    String routine = "";
    RoutineDetailAdapter adapter;

    public RoutineDetailFragment(String category, String routine) {
        this.category = category;
        this.routine = routine;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_routine_detail, container, false);

        HowToMachine howToMachine = new HowToMachine();
        howToMachine.setPullUp();
        TextView categoryView = view.findViewById(R.id.category_view);
        listView = view.findViewById(R.id.listView);
        adapter = new RoutineDetailAdapter();
        List<String> list = new ArrayList<>();
        listView.setAdapter(adapter);
        Button routineStart = view.findViewById(R.id.routine_start);
        categoryView.setText(this.category);

        for (String exercise : routine.split(" -> "))
            list.add(exercise);

        for(int i =0;i<list.size();i++){
            if(list.get(i).equals("풀업")){
                howToMachine.setPullUp();
                adapter.addItem(new RoutineDetailItem(i,list.get(i),howToMachine.getThumbnail1(),howToMachine.getTitle1(),howToMachine.getUrl1(),
                        howToMachine.getThumbnail2(),howToMachine.getTitle2(),howToMachine.getUrl2()));
            }
            else{
                howToMachine.setChinUp();
                adapter.addItem(new RoutineDetailItem(i,list.get(i),howToMachine.getThumbnail1(),howToMachine.getTitle1(),howToMachine.getUrl1(),
                        howToMachine.getThumbnail2(),howToMachine.getTitle2(),howToMachine.getUrl2()));
            }
        }
        return view;
    }
}



