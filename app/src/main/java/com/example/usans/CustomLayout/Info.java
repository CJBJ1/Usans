package com.example.usans.CustomLayout;


import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.DirectionsJSONParser;
import com.example.usans.MainActivity;
import com.example.usans.R;

import com.example.usans.SceneFragment.HomeFragment;
import com.example.usans.RequestHttpURLConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem2;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Info extends Fragment {
    private Activity activity;
    private Facility facility;
    private FacilityList facilityList;
    private ArrayList<Facility> list;
    private TMapView tMapView;
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
               /* MainActivity act = (MainActivity) getActivity();
                HomeFragment homeF =  act.homeFragment;
                homeF.showRecommend();*/
                closeInfo();
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.super.getActivity().onBackPressed();

                MainActivity main = (MainActivity) Info.super.getActivity();
                main.ca.roadMode = true;
                main.ca.setActionBar(5);
                getWalkPath(new TMapPoint(37.503149,126.952264),
                        new TMapPoint(Double.parseDouble(facility.getLat()),Double.parseDouble(facility.getLng())));
                getWalkDocument(new TMapPoint(37.503149,126.952264),
                        new TMapPoint(Double.parseDouble(facility.getLat()),Double.parseDouble(facility.getLng())));

                /*String url = "https://maps.googleapis.com/maps/api/directions/" +
                        "json?origin=37.503149,126.952264&destination="+facility.getLat()+","+facility.getLng()+"&mode=transit"+
                        "&key=AIzaSyCaqvwkL7Ho0RgE5yACxhSpQyGE7rXo2YI";

                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();*/
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

        layout();
        return view;
    }

    public void closeInfo() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this);
        ft.commit();
    }

    public void layout() {
//        imageView = facility.getPhoto();
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        machinesView.setText(facility.getMachines());
        ratingBar.setRating(facility.getRating());
        if(facility.getPhoto().length!=0) {
            Glide.with(getContext()).load(facility.getPhoto()[0]).into(imageView);
        }
    }


    public void getWalkPath(TMapPoint startPoint,TMapPoint endPoint){
        TMapData tMapData = new TMapData();
        tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint, endPoint, new TMapData.FindPathDataListenerCallback() {
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
        tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint,endPoint, new TMapData.FindPathDataAllListenerCallback() {
            @Override
            public void onFindPathDataAll(Document document) {
                Element root = document.getDocumentElement();
                NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");
                Log.d("NodeList",nodeListPlacemark + "");
                for( int i=0; i<nodeListPlacemark.getLength(); i++ ) {
                    NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                    Log.d("Item",nodeListPlacemarkItem + "");
                    for( int j=0; j<nodeListPlacemarkItem.getLength(); j++ ) {
                        if( nodeListPlacemarkItem.item(j).getNodeName().equals("description") ) {
                            Log.d("debug", nodeListPlacemarkItem.item(j).getTextContent().trim() );
                        }
                    }
                }
            }
        });
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url,values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("길찾아",s);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(s);
        }

    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

           /* for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }

            if (lineOptions != null) {
                if (facilityList.getPolyline() != null) {
                    facilityList.getPolyline().remove();
                }
                facilityList.setPolyline(mMap.addPolyline(lineOptions));
            }


            for(int i=0;i<facilityList.getArrayList().size();i++){
                if(facilityList.getArrayList().get(i).getId()==facility.getId()){
                    continue;
                }
                else{
                    facilityList.getArrayList().get(i).getMarker().setVisible(false);
                }
            }


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng((37.503149+Double.parseDouble(facility.getLat()))/2.0,
                    (126.952264+Double.parseDouble(facility.getLng()))/2.0),11));*/
        }

    }
}
