package com.example.usans.SceneFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.usans.Adapter.RoutineAdapter;
import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.AppHelper;
import com.example.usans.Data.RoutineItem;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoutineFragment extends Fragment {
    View view;

    ListView listView;
    RoutineAdapter adapter;

    FragmentManager fm;
    String machines;
    String[] machine;

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

        machine = machines.split(" ");
        NetworkTask networkTask = new NetworkTask(AppHelper.Routine, null);
        networkTask.execute();

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

    public boolean check(String routine) {
        if (routine.length() == 0) return false;
        for (String mach : routine.split(" -> ")) {
            if (mach.equals("풀업") || mach.equals("친업") && machines.contains("철봉"))
                continue;
            if (mach.equals("딥스") && machines.contains("평행봉"))
                continue;
            if (mach.equals("벤치프레스") && machines.contains("스미스머신"))
                continue;
            if (mach.equals("스쿼트"))
                continue;
            if (!machines.contains(mach))
                return false;
        }
        return true;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsArr = new JSONArray(s);
                int index = jsArr.length() - 1;
                while (index != -1) {
                    JSONObject jsonObject = jsArr.getJSONObject(index);
                    if (check(jsonObject.getString("helptext"))) {
                        adapter.addItem(new RoutineItem(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("helptext")));
                    }
                    index--;
                }

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
