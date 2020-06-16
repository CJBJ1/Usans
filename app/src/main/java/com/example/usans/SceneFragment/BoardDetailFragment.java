package com.example.usans.SceneFragment;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.usans.Adapter.TitleAdapter;
import com.example.usans.Adapter.TitleCommentAdapter;
import com.example.usans.AppHelper;
import com.example.usans.Data.FacilityList;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardDetailFragment extends Fragment {
    View view;
    ImageView writerImageView;
    TextView writerView;
    TextView timeView;
    TextView titleView;
    TextView contentsView;

    private FacilityList facilityList;
    int titleId;
    String userId;
    String passTime;
    String title;
    String contents;
    int boardNumber;

    ListView listView;
    TitleCommentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board_detail, container, false);

        writerImageView = (ImageView) view.findViewById(R.id.board_writer_image_view);
        writerView = (TextView) view.findViewById(R.id.board_writer);
        timeView = (TextView) view.findViewById(R.id.board_time);
        titleView = (TextView) view.findViewById(R.id.board_title);
        contentsView = (TextView) view.findViewById(R.id.board_contents);

        facilityList = (FacilityList) super.getActivity().getApplication();
        writerImageView.setImageResource(R.drawable.user1);
        writerView.setText(userId);
        timeView.setText(switchDateFormat(passTime));
        titleView.setText(title);
        contentsView.setText(contents);

        listView = view.findViewById(R.id.title_list_view);
        adapter = new TitleCommentAdapter();
        listView.setAdapter(adapter);

        final EditText contentEditText = view.findViewById(R.id.editText);
        Button writeCommentButton = view.findViewById(R.id.save_button);

        if (boardNumber != -1) {
            writeCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (facilityList.getUser() != null) {

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("post", titleId);
                        contentValues.put("user", facilityList.getUser().getId());
                        contentValues.put("text", contentEditText.getText().toString());
                        String url = "http://3.34.18.171:8000/reply/";
                        CommentNetworkTask commentNetworkTask = new CommentNetworkTask(url, contentValues);
                        commentNetworkTask.execute();
                        contentEditText.setText("");

                        String url2 = "http://3.34.18.171:8000/arti/read/?id=";
                        NetworkTask networkTask = new NetworkTask(url2 + titleId, null);
                        networkTask.execute();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("로그인이 필요합니다.");
                        builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                facilityList.setGoToBoardComment(boardNumber);
                                facilityList.setGoToTitleItem(new TitleItem(titleId, userId, passTime, title, contents, boardNumber));
                                getActivity().setResult(10003, intent);
                                getActivity().finish();
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });

            String url = "http://3.34.18.171:8000/arti/read/?id=";
            NetworkTask networkTask = new NetworkTask(url + titleId, null);
            networkTask.execute();
        } else {
            contentEditText.setVisibility(View.INVISIBLE);
            writeCommentButton.setVisibility(View.INVISIBLE);

            String url = "http://3.34.18.171.nip.io:8000/mypage/?id=";
            NetworkTask networkTask;
            if (facilityList.getUser() != null)
                networkTask = new NetworkTask(url + facilityList.getUser().getId(), null);
            else
                networkTask = new NetworkTask(url + 11, null);

            networkTask.execute();
        }
        return view;
    }

    public BoardDetailFragment(int titleId, String userId, String passTime, String title, String contents, int boardNumber) {
        this.titleId = titleId;
        this.userId = userId;
        this.passTime = passTime;
        this.title = title;
        this.contents = contents;
        this.boardNumber = boardNumber;
    }

    public class CommentNetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public CommentNetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.requestBody(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
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
                JSONArray jsArr;
                if (boardNumber != -1) {
                    JSONObject jsonObject = new JSONObject(s);
                    jsArr = jsonObject.getJSONArray("reply");
                } else {
                    jsArr = new JSONArray(s);
                }
                int index = 0;
                adapter = new TitleCommentAdapter();

                while (index != jsArr.length()) {
                    JSONObject titlejson = jsArr.getJSONObject(index);
                    adapter.addItem(new TitleItem(titlejson.getString("username"), titlejson.getString("text"), titlejson.getString("time").substring(0, 19)));
                    index++;
                }

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String switchDateFormat(String date) {
        // SimpleDateFormat의 형식을 선언한다.
        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd");
        String new_date = "";
        // 날짜 형식 변환시 파싱 오류를 try.. catch..로 체크한다.
        try {
            // 문자열 타입을 날짜 타입으로 변환한다.
            Date original_date = original_format.parse(date);

            // 날짜 형식을 원하는 타입으로 변경한다.
            new_date = new_format.format(original_date);


        } catch (ParseException e) {
            e.printStackTrace();

        }
        return new_date;
    }
}
