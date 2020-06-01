package com.example.usans.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.usans.Data.RoutineItem;
import com.example.usans.Data.RoutineItemView;
import java.util.ArrayList;

public class RoutineAdapter extends BaseAdapter {
    ArrayList<RoutineItem> items = new ArrayList<>();

    public void addItem(RoutineItem item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoutineItemView view;
        if (convertView == null) {
            view = new RoutineItemView(parent.getContext());
        } else {
            view = (RoutineItemView) convertView;
        }

        RoutineItem item = items.get(position);

        view.setItemId(item.getId());
        view.setItemCategory(item.getCategory());
        view.setItemContents(item.getRoutine());

        return view;
    }
}
