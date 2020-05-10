package com.example.usans.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityItemView;

import java.util.ArrayList;

public class FacilityAdapter extends BaseAdapter {
    ArrayList<Facility> items = new ArrayList<Facility>();

    public void addItem(Facility item){
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
        FacilityItemView view;
        if (convertView == null) {
            view = new FacilityItemView(parent.getContext());
        } else {
            view = (FacilityItemView) convertView;
        }

        Facility item = items.get(position);

        view.setItemId(item.getId());
        view.setItemImage();
        view.setItemName(item.getName());
        view.setItemAddress(item.getAddress());
        view.setItemMachines(item.getMachines());
        view.setItemRating(item.getRating());

        return view;
    }

}