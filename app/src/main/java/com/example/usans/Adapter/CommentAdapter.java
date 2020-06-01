package com.example.usans.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.usans.Data.CommentItem;
import com.example.usans.Data.CommentItemView;
import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    ArrayList<CommentItem> items = new ArrayList<CommentItem>();

    public void addItem(CommentItem item){
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
        CommentItemView view;
        if (convertView == null) {
            view = new CommentItemView(parent.getContext());
        } else {
            view = (CommentItemView) convertView;
        }

        CommentItem item = items.get(position);

        view.setItemId(item.getId());
        view.setItemImage(item.getRating());
        view.setItemWriter(item.getWriter());
        view.setItemTime(item.getTime());
        view.setItemComment(item.getContents());
        view.setItemRating(item.getRating());
        view.setItemMachine(item.getMachine(), item.getRating());

        return view;
    }

}
