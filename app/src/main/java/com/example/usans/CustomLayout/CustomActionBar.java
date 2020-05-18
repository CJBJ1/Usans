package com.example.usans.CustomLayout;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.example.usans.Data.FacilityList;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.skt.Tmap.TMapView;

public class CustomActionBar {
    private Activity activity;
    private ActionBar actionBar;
    private FacilityList facilityList;
    View mCustomView;

    public TextView dongTextView;
    public Button dongButton;
    public Button addButton;

    public TextView titleText;
    public ImageView medalView;

    public Button closeButton;

    public TextView stepCountView;
    public Button arrivalButton;

    public boolean roadMode;

    public CustomActionBar(Activity _activity, ActionBar _actionBar) {
        this.activity = _activity;
        this.actionBar = _actionBar;
        roadMode = false;

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

    }

    public void setActionBar(int n) {
        switch (n) {
            case 0:
                if (roadMode) {
                    setActionBar(5);
                    break;
                }
                mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar, null);
                actionBar.setCustomView(mCustomView);

                dongTextView = mCustomView.findViewById(R.id.dong_textView);
                dongButton = mCustomView.findViewById(R.id.dong_button);
                addButton = mCustomView.findViewById(R.id.add_button);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setActionBar(4);
                        MainActivity main = (MainActivity) activity;
                        main.homeFragment.showAddMarkerButton();
                    }
                });
                MainActivity main = (MainActivity) activity;
                if (main.homeFragment.addMarker != null) main.homeFragment.addMarker.setVisibility(View.INVISIBLE);
                break;
            case 1:
                setActionBar(0);
                addButton.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar2, null);
                actionBar.setCustomView(mCustomView);
                break;
            case 3:
                mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar3, null);
                actionBar.setCustomView(mCustomView);

                titleText = mCustomView.findViewById(R.id.title_text);
                medalView = mCustomView.findViewById(R.id.medal_view);
                break;
            case 4:
                mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar4, null);
                actionBar.setCustomView(mCustomView);

                closeButton = mCustomView.findViewById(R.id.close_button);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setActionBar(0);
                        MainActivity main = (MainActivity) activity;
                        main.homeFragment.addMarker.setVisibility(View.INVISIBLE);
                        main.homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 5:
                mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar5, null);
                actionBar.setCustomView(mCustomView);

                stepCountView = mCustomView.findViewById(R.id.step_count_view);
                arrivalButton = mCustomView.findViewById(R.id.arrival_button);
                arrivalButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        roadMode = false;
                        setActionBar(0);
                        MainActivity main = (MainActivity) activity;
                        facilityList = (FacilityList)activity.getApplication();
                        int size = facilityList.getArrayList().size();
                        TMapView tMapView = facilityList.gettMapView();
                        for(int i=0;i<size;i++) {
                            tMapView.addMarkerItem2(String.valueOf(i),facilityList.getArrayList().get(i).getMarker());
                        }
                        tMapView.removeTMapPath();
                        //main.homeFragment.showRecommend();
                    }
                });
                break;
        }

        setExtend();
    }

    public void setExtend() {
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);
    }

}
