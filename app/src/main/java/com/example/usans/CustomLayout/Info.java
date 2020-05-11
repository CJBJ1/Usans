package com.example.usans.CustomLayout;

import android.content.ContentValues;
import android.graphics.Color;
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

import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.DirectionsJSONParser;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Info extends Fragment {

    private Facility facility;
    private FacilityList facilityList;
    private GoogleMap mMap;
    List<List<HashMap<String, String>>> routes = null;

    View view;
    Button closeButton, detailButton, startButton;

    ImageView imageView;
    TextView nameView, addressView, machinesView;
    RatingBar ratingBar;


    public Info (Facility facility, GoogleMap mMap) {
        this.facility = facility;
        this.mMap = mMap;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_view, container, false);
        facilityList = (FacilityList)getActivity().getApplication();
        imageView = view.findViewById(R.id.sans_image_view);
        nameView = view.findViewById(R.id.sans_name);
        addressView = view.findViewById(R.id.sans_address);
        machinesView = view.findViewById(R.id.sans_machines);
        ratingBar = view.findViewById(R.id.sans_ratingBar);

        closeButton = view.findViewById(R.id.close_button);
        detailButton = view.findViewById(R.id.detail_button);
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
                MainActivity main = (MainActivity) Info.super.getActivity();
                main.moveToDetail(facility);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.super.getActivity().onBackPressed();

                MainActivity main = (MainActivity) Info.super.getActivity();
                main.ca.setActionBar(5);

                String url = "https://maps.googleapis.com/maps/api/directions/" +
                        "json?origin=37.5670135,126.9783740&destination="+facility.getLat()+","+facility.getLng()+"&mode=transit"+
                        "&key=AIzaSyCaqvwkL7Ho0RgE5yACxhSpQyGE7rXo2YI";


                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
            }
        });

        layout();
        return view;
    }

    public void layout() {
//        imageView = facility.getPhoto();
        nameView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        machinesView.setText(facility.getMachines());
        ratingBar.setRating(facility.getRating());
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

            for (int i = 0; i < result.size(); i++) {
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

                // Adding all the points in the route to LineOptions
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
        }
    }
}
