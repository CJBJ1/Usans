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
import com.example.usans.R;

public class CustomActionBar {
    private Activity activity;
    private ActionBar actionBar;
    View mCustomView;

    public TextView dongTextView;
    public Button dongButton;
    public Button addButton;

    public Button searchButton;
    public EditText searchText;
    public Button writeButton;

    public TextView titleText;
    public ImageView medalView;

    public Button closeButton;

    public CustomActionBar(Activity _activity, ActionBar _actionBar) {
        this.activity = _activity;
        this.actionBar = _actionBar;

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

    }

    public void setActionBar(int n) {
        switch (n) {
            case 0:
                mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar, null);
                actionBar.setCustomView(mCustomView);

                dongTextView = mCustomView.findViewById(R.id.dong_textView);
                dongButton = mCustomView.findViewById(R.id.dong_button);
                addButton = mCustomView.findViewById(R.id.add_button);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setActionBar(4);
                    }
                });
                break;
            case 1:
                setActionBar(0);
                addButton.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar2, null);
                actionBar.setCustomView(mCustomView);

                searchButton = mCustomView.findViewById(R.id.search_button);
                searchText = mCustomView.findViewById(R.id.search_text);
                writeButton = mCustomView.findViewById(R.id.write_button);
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
