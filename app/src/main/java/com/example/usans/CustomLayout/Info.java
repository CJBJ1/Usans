package com.example.usans.CustomLayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.example.usans.Adapter.RouteAdapter;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.Data.RouteItem;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.example.usans.SceneFragment.HomeFragment;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Info extends Fragment {
    private Activity activity;
    private Facility facility;
    private FacilityList facilityList;
    private ArrayList<Facility> list;
    private TMapView tMapView;
    private FragmentManager fm;
    private Button tAppButton;
    List<List<HashMap<String, String>>> routes = null;

    View view;
    Button closeButton, detailButton, routineRecommendButton, startButton;

    ImageView imageView;
    ImageView imageView2;
    TextView nameView, addressView, machinesView;
    RatingBar ratingBar;

    public Info (Facility facility,TMapView tMapView) {
        this.facility = new Facility(facility);
        this.tMapView = tMapView;
    }

    public Info(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_view, container, false);
        facilityList = (FacilityList)getActivity().getApplication();
        imageView = view.findViewById(R.id.sans_image_view);
        imageView2 = (ImageView)view.findViewById(R.id.sans_sub_image_view);
        nameView = view.findViewById(R.id.sans_name);
        addressView = view.findViewById(R.id.sans_address);
        machinesView = view.findViewById(R.id.sans_machines);
        ratingBar = view.findViewById(R.id.sans_ratingBar);

        closeButton = view.findViewById(R.id.close_button);
        detailButton = view.findViewById(R.id.detail_button);
        routineRecommendButton = view.findViewById(R.id.routine_recommend_button);
        startButton = view.findViewById(R.id.sans_navigation_start_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info.super.getActivity().onBackPressed();
            }
        });
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.moveToDetail(facility);
            }
        });
        routineRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity act = (MainActivity) getActivity();
                HomeFragment homeF =  act.homeFragment;
                homeF.showRecommend(facility.getMachines());
                closeInfo();
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMapTapi tMapTapi = new TMapTapi(getActivity().getApplicationContext());
                HashMap pathInfo = new HashMap();
                pathInfo.put("rGoName", "T타워");
                pathInfo.put("rGoX", "126.985302");
                pathInfo.put("rGoY", "37.570841");

                pathInfo.put("rStName", "출발지");
                pathInfo.put("rStX", "126.926252");
                pathInfo.put("rStY", "37.557607");

                pathInfo.put("rV1Name", "경유지");
                pathInfo.put("rV1X", "126.976867");
                pathInfo.put("rV1Y", "37.576016");
                tMapTapi.invokeRoute(pathInfo);

                Info.super.getActivity().onBackPressed();

                fm = facilityList.getFm();
                MainActivity main = (MainActivity) Info.super.getActivity();
                main.ca.roadMode = true;
                main.ca.setActionBar(5);

                if(facility!=null) {
                    main.ca.setName(facility.getName());
                }

                getWalkPath(new TMapPoint(37.503149,126.952264),
                        new TMapPoint(Double.parseDouble(facility.getLat()),Double.parseDouble(facility.getLng())));
                getWalkDocument(new TMapPoint(37.503149,126.952264),
                        new TMapPoint(Double.parseDouble(facility.getLat()),Double.parseDouble(facility.getLng())));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("imageView", "클릭됨");
                MainActivity main = (MainActivity) getActivity();
                main.moveToImage(facility.getPhoto());
            }
        });

        if (this.facility !=null) layout();
        return view;
    }

    public void closeInfo() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this);
        ft.commit();
    }

    public void layout() {
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        StringBuilder machines = new StringBuilder();
        for (String machine : facility.getMachines().split(" ")) {
            machines.append(machine + "\t   ");
        }
        machinesView.setText(machines.toString());
        ratingBar.setRating(facility.getRating());
        if(facility.getPhoto().length!=0) {
            Glide.with(getContext()).load(facility.getPhoto()[0]).into(imageView);
        }
    }

    public void getWalkPath(TMapPoint startPoint,TMapPoint endPoint){
        TMapData tMapData = new TMapData();
        tMapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, startPoint, endPoint, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine polyLine) {
                polyLine.setID("result");
                tMapView.addTMapPath(polyLine);
                int mSize = facilityList.getArrayList().size();
                for(int i =0;i<mSize;i++){
                    tMapView.removeMarkerItem2(facilityList.getArrayList().get(i).getMarker().getID());
                }
            }
        });
    }

    public void getWalkDocument(TMapPoint startPoint,TMapPoint endPoint){
        TMapData tMapData = new TMapData();
        tMapData.findPathDataAllType(TMapData.TMapPathType.CAR_PATH, startPoint,endPoint, new TMapData.FindPathDataAllListenerCallback() {
            @Override
            public void onFindPathDataAll(Document document) {
                RouteAdapter adapter = new RouteAdapter();
                Element root = document.getDocumentElement();
                NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");
                Log.d("NodeList",nodeListPlacemark + "");
                for( int i=0; i<nodeListPlacemark.getLength(); i++ ) {
                    NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                    Log.d("Item",nodeListPlacemarkItem + "");
                    for( int j=0; j<nodeListPlacemarkItem.getLength(); j++ ) {
                        if( nodeListPlacemarkItem.item(j).getNodeName().equals("description") ) {
                            adapter.addItem(new RouteItem(nodeListPlacemarkItem.item(j).getTextContent().trim()));
                            Log.d("debug", nodeListPlacemarkItem.item(j).getTextContent().trim() );
                        }
                    }
                }

                fm.popBackStack();
                Fragment Route = new Route(adapter);
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_bottom,R.anim.enter_to_bottom,R.anim.enter_from_bottom,R.anim.enter_to_bottom);
                transaction.add(R.id.info, Route);
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });
    }
}
