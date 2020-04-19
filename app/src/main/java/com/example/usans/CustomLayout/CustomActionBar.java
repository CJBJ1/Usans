package com.example.usans.CustomLayout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.example.usans.R;

public class CustomActionBar {
    private Activity activity;
    private ActionBar actionBar;

    public TextView dongTextView;
    public Button dongButton;

    public CustomActionBar(Activity _activity, ActionBar _actionBar) {
        this.activity = _activity;
        this.actionBar = _actionBar;

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }

    public void setActionBar() {
        View mCustomView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar, null);
        actionBar.setCustomView(mCustomView);

        dongTextView = mCustomView.findViewById(R.id.dong_textView);
        dongButton = mCustomView.findViewById(R.id.dong_button);

        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);
    }

}
