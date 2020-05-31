package com.example.usans;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BalloonOverlayView extends FrameLayout {
    private LinearLayout layout;

    public BalloonOverlayView(Context context, String labelName, String id) {
        super(context);

        setPadding(10, 0, 10, 0);
        layout = new LinearLayout(context);
        layout.setVisibility(VISIBLE);

        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.NO_GRAVITY;
        addView(layout, params);
    }

}
