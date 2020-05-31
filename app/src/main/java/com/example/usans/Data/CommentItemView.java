package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.usans.R;

public class CommentItemView extends LinearLayout {
    int reviewId;
    ImageView writerImageView;
    TextView wirterView;
    TextView timeView;
    TextView contentsView;
    RatingBar ratingBar;
    TextView machineView;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_item_view, this, true);

        writerImageView = (ImageView) findViewById(R.id.writer_image_view);
        wirterView = (TextView) findViewById(R.id.sans_name);
        timeView = (TextView) findViewById(R.id.time_view);
        contentsView = (TextView) findViewById(R.id.sans_address);
        ratingBar = (RatingBar) findViewById(R.id.sans_ratingBar);
        machineView = findViewById(R.id.machine_view);
    }

    public void setItemId(int id) {reviewId = id;}
    public void setItemImage(float userRating) {
        if (userRating >= 0) writerImageView.setImageResource(R.drawable.user1);
        else writerImageView.setImageResource(R.drawable.qm);
    }
    public void setItemWriter(String userId) {wirterView.setText(userId);}
    public void setItemTime(String passTime) {timeView.setText(passTime);}
    public void setItemComment(String comment) {contentsView.setText(comment);}
    public void setItemRating(float userRating) {
        if (userRating >= 0) ratingBar.setRating(userRating);
        else ratingBar.setVisibility(INVISIBLE);
    }
    public void setItemMachine(String machine,float userRating) {
        machineView.setText(machine);
    }

}
