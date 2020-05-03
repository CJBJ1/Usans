package com.example.usans.SceneFragment;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.usans.CustomLayout.Info;
import com.example.usans.CustomLayout.Recommend;
import com.example.usans.Facility;
import com.example.usans.FacilityList;
import com.example.usans.MainActivity;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
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

        addMarkerButtom = view.findViewById(R.id.add_marker_button);
        sansNavigationStartButton = view.findViewById(R.id.sans_navigation_start_button);

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
        addMarkerButtom.setVisibility(View.VISIBLE);
        addMarkerButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("addMarkerButton", "클릭");
                MainActivity main = (MainActivity) getActivity();
                main.moveToAddSans();
            }
        });
    }

    @UiThread
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        facilityList = (FacilityList)getActivity().getApplicationContext();
        setMap();
        /*String url = "http://3.34.18.171.nip.io:8000/api/Sansuzang";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();*/
    }

    public void showInfo(FragmentManager fm){
        fm.popBackStack();

        Fragment inf = new Info();
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
                showInfo(infoFm);
                return false;
            }
        };

        Log.d("사이즈",facilityList.getArrayList().size() + "");

        for (int index =0;index<facilityList.getArrayList().size();index++) {
            MarkerOptions markerOptions = new MarkerOptions();
            Log.d("위도",facilityList.getArrayList().get(index).getLat());
            Log.d("경도",facilityList.getArrayList().get(index).getLng());
            markerOptions.position(new LatLng(Double.parseDouble(facilityList.getArrayList().get(index).getLat()),
                    Double.parseDouble(facilityList.getArrayList().get(index).getLng())));
            mMap.addMarker(markerOptions).setTag(index);
        }

        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(new LatLng(37.5670135, 126.9783740));
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(new LatLng(37.5640135, 126.9763740));
        mMap.addMarker(markerOptions1);
        mMap.addMarker(markerOptions2);

        LatLng center = new LatLng(37.5670135, 126.9783740);
        mMap.setOnMarkerClickListener(markerClickListener);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

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

                jsArr = new JSONArray(s);
                while(index != 100){
                    parseJS(jsArr,index);
                    index++;
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

                facilityList.getArrayList().add(facility);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
