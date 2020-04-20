package com.example.usans.SceneFragment;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usans.CustomLayout.Info;
import com.example.usans.Facility;
import com.example.usans.FacilityList;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

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
    View view;
    FragmentManager fm;
    LatLng infoLatLng;

    private JSONArray jsArr;
    private FacilityList facilityList;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();
        }

        fm = getFragmentManager();
        mapFragment.getMapAsync(this);
        return view;
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        final Marker marker = new Marker();
        final Marker marker2 = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740));
        marker2.setPosition(new LatLng(37.5640135, 126.9763740));
        marker.setMap(naverMap);
        marker2.setMap(naverMap);
        Overlay.OnClickListener onClickListener = new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                showInfo(fm);
                return false;
            }
        };
        marker.setOnClickListener(onClickListener);
        marker2.setOnClickListener(onClickListener);
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

    public void setNetwork(){
        String url = "http://54.180.83.196:8888/places";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
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
            result = requestHttpURLConnection.request(url,values,0);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                int index=0;

                jsArr = new JSONArray(s);
                while(index != jsArr.length()){
                    parseJS(jsArr,index);
                    index++;
                }

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
                facility.setLat(jsonObject.getString("latitude"));
                facility.setLng(jsonObject.getString("longitude"));

                facilityList.getArrayList().add(facility);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
