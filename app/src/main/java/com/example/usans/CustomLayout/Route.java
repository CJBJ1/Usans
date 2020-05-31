package com.example.usans.CustomLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.usans.Adapter.RouteAdapter;
import com.example.usans.Data.RouteItem;
import com.example.usans.Data.TitleItem;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Route extends Fragment {

    private ListView listView;
    private View view;
    private RouteAdapter adapter;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
    private int isCar;

    Route(double startLat,double startLng,double endLat,double endLng){
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat=endLat;
        this.endLng=endLng;
    }

    public Route(RouteAdapter adapter){this.adapter = adapter;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.route_list, container, false);
        listView = view.findViewById(R.id.route_list);
        listView.setAdapter(adapter);
        return view;
    }

    public void setIsCar(int isCar) {
        this.isCar = isCar;
    }

}
