package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usans.R;

public class RouteItemView extends LinearLayout {
    int id;
    TextView routeView;
    ImageView imageView;


    public RouteItemView(Context context) {
        super(context);
        init(context);
    }

    public RouteItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.route_item_view, this, true);
        routeView = findViewById(R.id.route_text);
        imageView = findViewById(R.id.route_image);
    }

    public void setItemRoute(String route) {routeView.setText(route);}
    public void setImageView(){
        imageView.setImageResource(R.drawable.car);
    }

}
