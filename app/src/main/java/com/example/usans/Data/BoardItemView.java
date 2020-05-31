package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.usans.R;

public class BoardItemView extends LinearLayout {
    int id;
    ImageView writerImageView;
    TextView writerView;
    TextView timeView;
    TextView titleView;
    TextView contentsView;

    public BoardItemView(Context context) {
        super(context);
        init(context);
    }

    public BoardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.board_detail_item_view, this, true);

        writerImageView = (ImageView) findViewById(R.id.board_writer_image_view);
        writerView = (TextView) findViewById(R.id.board_writer);
        timeView = (TextView) findViewById(R.id.board_time);
        titleView = (TextView) findViewById(R.id.board_title);
        contentsView = (TextView) findViewById(R.id.board_contents);
    }

    public void setItemId(int id) {id = id;}
    public void setItemImage(/*int resId*/) {writerImageView.setImageResource(R.drawable.user1);}
    public void setItemWriter(String userId) {writerView.setText(userId);}
    public void setItemTime(String passTime) {timeView.setText(passTime);}
    public void setItemTitle(String title) {titleView.setText(title);}
    public void setItemContents(String contents) {contentsView.setText(contents);}

}
