package com.example.usans.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.usans.Data.BoardItem;
import com.example.usans.Data.BoardItemView;
import com.example.usans.Data.RoutineDetailItem;
import com.example.usans.Data.RoutineDetailItemView;
import com.example.usans.Data.RoutineItemView;

import java.util.ArrayList;

public class RoutineDetailAdapter extends BaseAdapter {
    ArrayList<RoutineDetailItem> items = new ArrayList<RoutineDetailItem>();

    public void addItem(RoutineDetailItem item){
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
        RoutineDetailItemView view;
        if (convertView == null) {
            view = new RoutineDetailItemView(parent.getContext());
        } else {
            view = (RoutineDetailItemView) convertView;
        }

        RoutineDetailItem item = items.get(position);

        view.setItemId(item.getId());
        view.setItemName(item.getName());
        view.setItemThumbnail1(item.getThumbnail1());
        view.setItemThumbnail2(item.getThumbnail2());
        view.setUrl1(item.getUrl1());
        view.setUrl2(item.getUrl2());
        view.setTitle1(item.getTitle1());
        view.setTitle2(item.getTitle2());

        return view;
    }

}
