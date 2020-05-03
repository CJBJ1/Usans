package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.usans.AppHelper;
import com.example.usans.R;
import com.google.gson.Gson;


public class CommentItemView extends LinearLayout {
    int reviewId;
    ImageView writerImageView;
    TextView wirterView;
    TextView timeView;
    TextView contentsView;
    RatingBar ratingBar;
    TextView recommendView;
    Button recommendButton;

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
        wirterView = (TextView) findViewById(R.id.writer_view);
        timeView = (TextView) findViewById(R.id.time_view);
        contentsView = (TextView) findViewById(R.id.contents_view);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        recommendView = findViewById(R.id.recommend_view);
        recommendButton = findViewById(R.id.recommend_button);

        recommendButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //requestIncreaseRecommend(reviewId);
            }
        });
    }

    public void setItemId(int id) {reviewId = id;}
    public void setItemImage(/*int resId*/) {writerImageView.setImageResource(R.drawable.user1);}
    public void setItemWriter(String userId) {wirterView.setText(userId);}
    public void setItemTime(String passTime) {timeView.setText(passTime);}
    public void setItemComment(String comment) {contentsView.setText(comment);}
    public void setItemRating(float userRating) {ratingBar.setRating(userRating);}
    public void setItemRecommend(int recommend) {recommendView.setText(toString().valueOf(recommend));}

    public void requestIncreaseRecommend(final int id) {
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/increaseRecommend";
        url += "?" + "review_id=" + id;

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,    //GET 방식은 요청 path가 필요
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "에러발생", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        request.setShouldCache(false);
//        AppHelper.requestQueue.add(request);

    }

//    public void processResponse(String response) {
//        Gson gson = new Gson();
//
//        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
//        if (info.code == 200) {
//            try {
//                recommendCallback.resetComment();
//                Toast.makeText(getContext(), "추천 완료", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

}
