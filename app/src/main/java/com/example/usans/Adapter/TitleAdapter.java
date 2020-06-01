package com.example.usans.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.usans.Data.TitleItem;
import com.example.usans.Data.TitleItemView;
import java.util.ArrayList;

public class TitleAdapter extends BaseAdapter {
    ArrayList<TitleItem> items = new ArrayList<TitleItem>();

    public void addItem(TitleItem item){
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
        TitleItemView view;
        if (convertView == null) {
            view = new TitleItemView(parent.getContext());
        } else {
            view = (TitleItemView) convertView;
        }

        TitleItem item = items.get(position);

        view.setItemId(item.getId());
        view.setItemImage(/*item.getResId()*/);
        view.setItemWriter(item.getWriter());
        //view.setItemTime(item.getTime());
        view.setItemTitle(item.getTitle());
        view.setItemContents(item.getContents());

        return view;
    }

}
