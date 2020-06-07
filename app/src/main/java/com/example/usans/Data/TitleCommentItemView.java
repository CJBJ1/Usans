package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usans.R;

public class TitleCommentItemView extends LinearLayout {
    int id;
    ImageView writerImageView;
    TextView writerView;
    TextView timeView;
    TextView titleView;
    TextView contentsView;

    public TitleCommentItemView(Context context) {
        super(context);
        init(context);
    }

    public TitleCommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_comment_item_view, this, true);

        writerImageView = (ImageView) findViewById(R.id.tciv_writer_image_view);
        writerView = (TextView) findViewById(R.id.tciv_writer);
        timeView = (TextView) findViewById(R.id.tciv_time);
        contentsView = (TextView) findViewById(R.id.tciv_contents);
    }

    public void setItemId(int id) {id = id;}
    public void setItemImage(/*int resId*/) {writerImageView.setImageResource(R.drawable.user1);}
    public void setItemWriter(String writer) {writerView.setText(writer);}
    public void setItemTime(String passTime) {timeView.setText(passTime);}
    public void setItemContents(String contents) {contentsView.setText(contents);}
}
