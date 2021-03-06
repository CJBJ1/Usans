package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.usans.R;
import java.util.ArrayList;

public class FacilityItemView extends LinearLayout {
    String facilityId;
    ImageView sansImageView;
    TextView nameView;
    TextView addressView;
    TextView machinesView;
    TextView distanceView;
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
        distanceView = (TextView) findViewById(R.id.sans_distance);
    }

    public void setItemId(String id) { facilityId = id; }
    public void setItemImage(String[] urlList) {
        if(urlList.length>0) {
            Glide.with(getContext()).load(urlList[0]).into(sansImageView);
        }
    }
    public void setItemName(String name) { nameView.setText(name); }
    public void setItemAddress(String address) { addressView.setText(address); }
    public void setItemMachines(String machines) { machinesView.setText(machines); }
    public void setItemMachineList(ArrayList<String> machineList) {
        int surplus = machineList.size()-2;
        String lists;
        if(surplus>=1) {
            lists = machineList.get(0) + " " + machineList.get(1) + " " + "외 " + surplus + "종";
        }
        else if(surplus == 0){
                lists = machineList.get(0) + " " + machineList.get(1);
        }
        else{
            lists = machineList.get(0);
        }
        machinesView.setText(lists);

    }
    public void setItemRating(float userRating) { ratingBar.setRating(userRating); }
    public void setItemDistance(double distance){distanceView.setText(String.valueOf((Math.round(distance)/1000.0))+ "km");}

}
