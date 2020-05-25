package com.example.usans.Data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.usans.R;

public class RoutineItemView extends LinearLayout {
    int id;
    TextView categoryView;
    TextView contentsView;

    public RoutineItemView(Context context) {
        super(context);
        init(context);
    }

    public RoutineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.routine_item_view, this, true);

        categoryView = (TextView) findViewById(R.id.category_view);
        contentsView = (TextView) findViewById(R.id.contents_view);
    }

    public void setItemId(int id) {id = id;}
    public void setItemCategory(String category) { categoryView.setText(category); }
    public void setItemContents(String contents) { contentsView.setText(contents); }

}
