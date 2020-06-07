package com.example.usans.SceneFragment;

import android.content.ContentValues;
import android.content.DialogInterface;
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

import com.example.usans.Adapter.TitleCommentAdapter;
import com.example.usans.Data.FacilityList;
import com.example.usans.Data.TitleItem;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;

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
        timeView.setText(passTime);
        titleView.setText(title);
        contentsView.setText(contents);

        listView = view.findViewById(R.id.title_list_view);
        adapter = new TitleCommentAdapter();
        listView.setAdapter(adapter);

        adapter.addItem(new TitleItem("조범준", "주민 분들이 예뻐해주시나 보네요 애웅이들 얼굴이 편해보여요 ㅋㅋㅋ"));

        final EditText contentEditText = view.findViewById(R.id.editText);
        Button writeCommentButton = view.findViewById(R.id.save_button);
        writeCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facilityList.getUser()!=null) {
                    String url = "http://3.34.18.171.nip.io:8000/reply/?post="+titleId+"&user="+facilityList.getUser().getId()+"&text="+contentEditText.getText().toString();
                    NetworkTask networkTask = new NetworkTask(url, null);
                    networkTask.execute();
                    contentEditText.setText("");
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

        return view;
    }

    public BoardDetailFragment(int titleId, String userId,String passTime,String title,String contents){
        this.titleId = titleId;
        this.userId = userId;
        this.passTime = passTime;
        this.title = title;
        this.contents = contents;
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
            result = requestHttpURLConnection.requestBody(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
