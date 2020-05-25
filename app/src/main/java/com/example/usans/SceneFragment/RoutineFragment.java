package com.example.usans.SceneFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.usans.Adapter.RoutineAdapter;
import com.example.usans.Data.RoutineItem;
import com.example.usans.R;

public class RoutineFragment extends Fragment {
    View view;

    ListView listView;
    RoutineAdapter adapter;

    FragmentManager fm;
    String machines;

    public RoutineFragment(String machines) {
        this.machines = machines;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_routine, container, false);

        listView = view.findViewById(R.id.listview);
        adapter = new RoutineAdapter();
        fm = getFragmentManager();

        String machine[] = machines.split(" ");
        //machines 로 기구 목록 뽑아내고 가능한 루틴 구하기
        //임시 데이터
        adapter.addItem(new RoutineItem(0, "가슴", "벤치프레스 -> 덤벨프레스 -> 딥스 -> 덤벨플라이"));
        adapter.addItem(new RoutineItem(0, "등", "풀업 -> 랫풀다운 -> 바벨로우"));

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fm.popBackStack();
                RoutineItem data = (RoutineItem) adapterView.getItemAtPosition(i);
                RoutineDetailFragment detail = new RoutineDetailFragment(data.category, data.routine);

                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.enter_to_left,R.anim.enter_from_left,R.anim.enter_to_right);
                transaction.replace(R.id.routine_frame, detail);
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });

        return view;
    }

}
