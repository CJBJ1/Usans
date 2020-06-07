package com.example.usans.SceneFragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.usans.Activity.DetailActivity;
import com.example.usans.Activity.WriteActivity;
import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.AppHelper;
import com.example.usans.Data.FacilityList;
import com.example.usans.Data.TitleItem;
import com.example.usans.FileUploadUtils;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BoardFragment extends Fragment {
    private FileUploadUtils fileUploadUtils;
    View view;
    ListView listView;
    TitleAdapter adapter;
    FragmentManager fm;
    Button writeButton;

    int boardNumber;
    NetworkTask networkTask;
    private FacilityList facilityList;

    public BoardFragment () {}
    public BoardFragment (int boardNumber) {
        this.boardNumber = boardNumber;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board, container, false);

        listView = view.findViewById(R.id.board_list_view);
        fm = getFragmentManager();
        facilityList = (FacilityList) super.getActivity().getApplication();
        adapter = new TitleAdapter();
        listView.setAdapter(adapter);

        writeButton = view.findViewById(R.id.write_board);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (facilityList.getUser()!=null) {
                    Intent intent = new Intent(getActivity(), WriteActivity.class);
                    intent.putExtra("boardNumber", boardNumber);
                    startActivityForResult(intent,22);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("로그인이 필요합니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        networkTask = new NetworkTask(AppHelper.Post, null);
        networkTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                fm.popBackStack();
                TitleItem data = (TitleItem) adapterView.getItemAtPosition(i);
                BoardDetailFragment detail = new BoardDetailFragment(data.id, data.getWriter(),data.getTime(),data.getTitle(),data.getContents());

                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right,R.anim.enter_to_left,R.anim.enter_from_left,R.anim.enter_to_right);
                transaction.replace(R.id.board_frameLayout, detail);
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        networkTask = new NetworkTask(AppHelper.Post, null);
        networkTask.execute();
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
                adapter = new TitleAdapter();
                while (index != -1) {
                    JSONObject jsonObject = jsArr.getJSONObject(index);
                    if (boardNumber == jsonObject.getInt("board")) {
                        adapter.addItem(new TitleItem(jsonObject.getInt("id"), jsonObject.getString("authorname"), jsonObject.getString("editdate"), jsonObject.getString("title"), jsonObject.getString("text")));
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
