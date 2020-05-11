package com.example.usans.SceneFragment;

import android.content.ContentValues;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usans.CustomLayout.Info;
import com.example.usans.CustomLayout.Recommend;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment
        implements OnMapReadyCallback {
    private View view;
    private FragmentManager fm;
    private FragmentManager infoFm;
    private SupportMapFragment mapFragment;
    private FacilityList facilityList;
    private JSONArray jsArr;
    private GoogleMap mMap;
    private LatLng userLocation;

    public ImageView addMarker;
    public Button addMarkerButtom;
    public Button sansNavigationStartButton;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        fm = getChildFragmentManager();
        infoFm = getFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        facilityList = (FacilityList)getActivity().getApplicationContext();

        addMarker = view.findViewById(R.id.marker_image);
        addMarkerButtom = view.findViewById(R.id.add_marker_button);
        sansNavigationStartButton = view.findViewById(R.id.sans_navigation_start_button);
        userLocation = new LatLng(37.5670135, 126.9783740);
        return view;
    }

    public void showRecommend(){
        infoFm.popBackStack();

        Fragment recommend = new Recommend();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_bottom,R.anim.enter_to_bottom,R.anim.enter_from_bottom,R.anim.enter_to_bottom);
        transaction.replace(R.id.info, recommend);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showAddMarkerButton(){
        addMarker.setVisibility(View.VISIBLE);
        addMarkerButtom.setVisibility(View.VISIBLE);
        addMarkerButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("addMarkerButton", "클릭");
                MainActivity main = (MainActivity) getActivity();
                main.moveToAddSans();   // 맵의 중앙 위치 전송
            }
        });
    }

    @UiThread
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        facilityList = (FacilityList)getActivity().getApplicationContext();
        String url = "http://3.34.18.171:8000/api/Sansuzang";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
    }

    public void showInfo(FragmentManager fm,int index){
        fm.popBackStack();
        Facility facility;

        if(index ==999) {
            facility = new Facility("1", "고구동산", "서울시 동작구 상도 1동 325-9", new String[]{"image url"}, "허리돌리기 철봉 평행봉", "0.0", "0.0", 3);
        }else {
            facility = new Facility(facilityList.getArrayList().get(index));
        }
        Fragment inf = new Info(facility,mMap);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_bottom,R.anim.enter_to_bottom,R.anim.enter_from_bottom,R.anim.enter_to_bottom);
        transaction.add(R.id.info, inf);
        transaction.commit();
        transaction.addToBackStack(null);
    }

    public void setMap(){
        GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTag().toString() != "테스트") {
                    showInfo(infoFm, Integer.parseInt(marker.getTag().toString()));
                }
                else{

                    showInfo(infoFm, 999);
                }
                return false;
            }
        };

        Log.d("사이즈",facilityList.getArrayList().size() + "");

        for (int index =0;index<facilityList.getArrayList().size();index++) {
            MarkerOptions markerOptions = new MarkerOptions();
            Double lat = Double.parseDouble(facilityList.getArrayList().get(index).getLat());
            Double lng = Double.parseDouble(facilityList.getArrayList().get(index).getLng());
            Log.d("위도",lat + "");
            Log.d("경도",lng + "");
            markerOptions.position(new LatLng(lat,lng));
            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(index);
            facilityList.getArrayList().get(index).setMarker(marker);
        }

        /*MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(new LatLng(37.5670135, 126.9783740));
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(new LatLng(37.5640135, 126.9763740));
        mMap.addMarker(markerOptions1).setTag("테스트");
        mMap.addMarker(markerOptions2).setTag("테스트");*/

        mMap.setOnMarkerClickListener(markerClickListener);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.5670135, 126.9783740),15));

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
            try {
                int index=0;
                if (s != null) {
                    jsArr = new JSONArray(s);
                    while (index != 50) {
                        parseJS(jsArr, index);
                        index++;
                    }
                }
                setMap();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void parseJS(JSONArray jsonArray, int index){
            Facility facility = new Facility();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                facility.setId(jsonObject.getString("id"));
                facility.setName(jsonObject.getString("name"));
                facility.setAddress(jsonObject.getString("address"));
                facility.setLat(jsonObject.getString("lat"));
                facility.setLng(jsonObject.getString("lon"));
                facility.setDistance(getDistance(userLocation,
                        new LatLng(Double.parseDouble(jsonObject.getString("lat")),
                                Double.parseDouble(jsonObject.getString("lon")))));

                facilityList.getArrayList().add(facility);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public double getDistance(LatLng LatLng1, LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.latitude);
        locationA.setLongitude(LatLng1.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);
        distance = locationA.distanceTo(locationB);

        return distance;
    }
}
