package com.example.usans.CustomLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.usans.Adapter.RouteAdapter;
import com.example.usans.Data.RouteItem;
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

    Route(double startLat,double startLng,double endLat,double endLng){
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat=endLat;
        this.endLng=endLng;
    }

    Route(RouteAdapter adapter) {this.adapter = adapter;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.route_list, container, false);

        listView = view.findViewById(R.id.route_list);
        listView.setAdapter(adapter);

        return view;
    }

    public void getWalkDocument(TMapPoint startPoint, TMapPoint endPoint){
        TMapData tMapData = new TMapData();
        tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint,endPoint, new TMapData.FindPathDataAllListenerCallback() {
            @Override
            public void onFindPathDataAll(Document document) {
                Element root = document.getDocumentElement();
                adapter = new RouteAdapter();
                NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");
                for( int i=0; i<nodeListPlacemark.getLength(); i++ ) {
                    NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                    for( int j=0; j<nodeListPlacemarkItem.getLength(); j++ ) {
                        if( nodeListPlacemarkItem.item(j).getNodeName().equals("description") ) {
                            adapter.addItem(new RouteItem(nodeListPlacemarkItem.item(j).getTextContent().trim()));
                            Log.d("debug", nodeListPlacemarkItem.item(j).getTextContent().trim() );
                        }
                    }
                }
            }
        });
    }

}
