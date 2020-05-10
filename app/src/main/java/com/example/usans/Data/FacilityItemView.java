package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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


public class FacilityItemView extends LinearLayout {
    String facilityId;
    ImageView sansImageView;
    TextView nameView;
    TextView addressView;
    TextView machinesView;
    RatingBar ratingBar;

    public FacilityItemView(Context context) {
        super(context);
        init(context);
    }

    public FacilityItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sans_item_view, this, true);

        sansImageView = (ImageView) findViewById(R.id.sans_image_view);
        nameView = (TextView) findViewById(R.id.sans_name);
        addressView = (TextView) findViewById(R.id.sans_address);
        machinesView = (TextView) findViewById(R.id.sans_machines);
        ratingBar = (RatingBar) findViewById(R.id.sans_ratingBar);
    }

    public void setItemId(String id) { facilityId = id; }
    public void setItemImage(/*int resId*/) { /*sansImageView.setImageResource(R.drawable.user1);*/ }
    public void setItemName(String name) { nameView.setText(name); }
    public void setItemAddress(String address) { addressView.setText(address); }
    public void setItemMachines(String machines) { machinesView.setText(machines); }
    public void setItemRating(float userRating) { ratingBar.setRating(userRating); }

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