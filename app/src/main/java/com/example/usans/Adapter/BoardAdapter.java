package com.example.usans.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.usans.Data.BoardItem;
import com.example.usans.Data.BoardItemView;
import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    ArrayList<BoardItem> items = new ArrayList<BoardItem>();

    public void addItem(BoardItem item){
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
        BoardItemView view;
        if (convertView == null) {
            view = new BoardItemView(parent.getContext());
        } else {
            view = (BoardItemView) convertView;
        }

        BoardItem item = items.get(position);

        view.setItemId(item.getId());
        view.setItemImage(/*item.getResId()*/);
        view.setItemWriter(item.getWriter());
        view.setItemTime(item.getTime());
        view.setItemTitle(item.getTitle());
        view.setItemContents(item.getContents());

        return view;
    }

}
