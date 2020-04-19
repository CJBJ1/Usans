package com.example.usans.SceneFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usans.CustomLayout.Info;
import com.example.usans.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;

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
}
