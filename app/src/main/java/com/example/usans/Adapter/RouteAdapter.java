package com.example.usans.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.usans.CustomLayout.Route;
import com.example.usans.Data.BoardItem;
import com.example.usans.Data.BoardItemView;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.CommentItemView;
import com.example.usans.Data.RouteItem;
import com.example.usans.Data.RouteItemView;
import com.example.usans.Data.TitleItemView;

import java.util.ArrayList;

public class RouteAdapter extends BaseAdapter {
    ArrayList<RouteItem> items = new ArrayList<>();

    public void addItem(RouteItem item){
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
        RouteItemView view;
        if (convertView == null) {
            view = new RouteItemView(parent.getContext());
        } else {
            view = (RouteItemView) convertView;
        }

        RouteItem item = items.get(position);
        view.setItemRoute(item.getRoute());
        return view;
    }

}
