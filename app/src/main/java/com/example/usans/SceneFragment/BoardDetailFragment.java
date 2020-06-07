package com.example.usans.SceneFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.usans.Data.FacilityList;
import com.example.usans.R;

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

        EditText contentEditText = view.findViewById(R.id.editText);
        Button writeCommentButton = view.findViewById(R.id.save_button);
        writeCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (facilityList.getUser()!=null) {
                    //networktask 전송
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

}
