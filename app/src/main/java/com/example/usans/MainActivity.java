package com.example.usans;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.usans.Activity.AddSansAcitivity;
import com.example.usans.Activity.BoardActivity;
import com.example.usans.Activity.DetailActivity;
import com.example.usans.Activity.ImageActivity;
import com.example.usans.Activity.RoutineActivity;
import com.example.usans.Adapter.RouteAdapter;
import com.example.usans.CustomLayout.Info;
import com.example.usans.CustomLayout.Route;
import com.example.usans.Data.RouteItem;
import com.example.usans.CustomLayout.CustomActionBar;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.SceneFragment.HomeFragment;
import com.example.usans.SceneFragment.ListFragment;
import com.example.usans.SceneFragment.MypageFragment;
import com.example.usans.SceneFragment.RegFragment;
import com.google.android.gms.maps.model.LatLng;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import net.danlew.android.joda.JodaTimeAndroid;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FacilityList facilityList;
    private Facility selectedFacility;
    private FragmentManager fm;
    private Info selectedInfo;
    private DrawerLayout mDrawerLayout;
    List<LatLng> latLngs;
    Button homeButton,listButton,regButton,mypageButton;
    FragmentTransaction tran;

    public HomeFragment homeFragment;
    ListFragment listFragment;
    MypageFragment mypageFragment;
    RegFragment regFragment;

    public CustomActionBar ca;

    RelativeLayout homeLayout, listLayout, regLayout, mypageLayout, heartLayout;

    int barMode=0;
    private TMapView tMapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_main);
        facilityList = (FacilityList)getApplication();
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ca = new CustomActionBar(this, getSupportActionBar());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        homeButton = (Button)findViewById(R.id.homebutton);
        listButton = (Button)findViewById(R.id.listbutton);
        regButton = (Button)findViewById(R.id.regbutton);
        mypageButton = (Button)findViewById(R.id.mypagebutton);

        homeLayout = findViewById(R.id.homelayout);
        listLayout = findViewById(R.id.listlayout);
        regLayout = findViewById(R.id.reglayout);
        mypageLayout = findViewById(R.id.mypagelayout);
        heartLayout = findViewById(R.id.heartlayout);

        homeButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
        mypageButton.setOnClickListener(this);

        homeFragment = new HomeFragment();
        listFragment = new ListFragment();
        mypageFragment = new MypageFragment();
        regFragment = new RegFragment();

//        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, heartFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, mypageFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, regFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, homeFragment).commit();
        setBackground(0);
        //ca.setActionBar(0);
    }

    public void moveToRoutine(String machines) {
        Intent intent = new Intent(this, RoutineActivity.class);
        intent.putExtra("machines", machines);
        startActivity(intent);
    }

    public void moveToImage(String[] photos) {
        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("photos", photos);
        startActivity(intent);
    }

    public void moveToBoard(int boardNumber) {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("boardNumber", boardNumber);
        startActivity(intent);
    }

    public void moveToAddSans() {
        Intent intent = new Intent(this, AddSansAcitivity.class);
        startActivity(intent);
    }

    public void moveToDetail(Facility facility) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("facility", facility);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.homebutton:
                setBackground(0);
                setFrag(0);
                break;
            case R.id.listbutton:
                setBackground(1);
                setFrag(1);
                listFragment.updateAdapter();
                break;
            case R.id.regbutton:
                setBackground(2);
                setFrag(2);
                break;
            case R.id.mypagebutton:
                setBackground(3);
                setFrag(3);
                break;
        }
    }

    public void setBackground(int n) {
        switch (n) {
            case 0:
                homeLayout.setBackgroundColor(Color.LTGRAY);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 1:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.LTGRAY);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 2:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.LTGRAY);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 3:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.LTGRAY);
                heartLayout.setBackgroundColor(Color.WHITE);
                break;
            case 4:
                homeLayout.setBackgroundColor(Color.WHITE);
                listLayout.setBackgroundColor(Color.WHITE);
                regLayout.setBackgroundColor(Color.WHITE);
                mypageLayout.setBackgroundColor(Color.WHITE);
                heartLayout.setBackgroundColor(Color.LTGRAY);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(barMode==0) {
            switch (item.getItemId()) {
                case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;
                }
                case R.id.menu_add: {
                    homeFragment.showAddMarkerButton();
                }
            }
        }
        else if(barMode == 1) {
            switch (item.getItemId()) {
                case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                    onBackPressed();
                    return true;
                }
                case R.id.routeBar_walk: {
                    invalidRoute(0);
                    return true;
                }
                case R.id.routeBar_car: {
                    invalidRoute(1);
                    return true;
                }
                case R.id.routeBar_ok: {
                    onBackPressed();
//                    homeFragment.popChild();

                    setBarMode(0);
                    invalidateOptionsMenu();
                    int size = facilityList.getArrayList().size();
                    TMapView tMapView = facilityList.gettMapView();
                    for(int i=0;i<size;i++) {
                        tMapView.addMarkerItem2(String.valueOf(i),facilityList.getArrayList().get(i).getMarker());
                    }
                    tMapView.removeTMapPath();
                }
            }
        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(barMode==0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        else if(barMode==1){
            getMenuInflater().inflate(R.menu.route, menu);
        }
        return true;
    }

    public void setFrag(int n) {    //프래그먼트 교체 메소드
        //ca.setActionBar(n);
        switch (n) {
            case 0:
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(homeFragment).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(listFragment).commit();
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(regFragment).commit();
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().show(mypageFragment).commit();
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void setBarMode(int barMode) {
        this.barMode = barMode;
    }

    public int getBarMode() {
        return barMode;
    }

    public void setSelectedFacility(Facility selectedFacility) {
        this.selectedFacility = selectedFacility;
    }

    public Facility getSelectedFacility() {
        return selectedFacility;
    }

    public void getPath(TMapPoint startPoint, TMapPoint endPoint, int isCar){
        TMapData tMapData = new TMapData();
        if(isCar==1) {
            tMapData.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, startPoint, endPoint, new TMapData.FindPathDataListenerCallback() {
                @Override
                public void onFindPathData(TMapPolyLine polyLine) {
                    polyLine.setID("result");
                    tMapView = facilityList.gettMapView();
                    tMapView.addTMapPath(polyLine);
                    int mSize = facilityList.getArrayList().size();
                    for (int i = 0; i < mSize; i++) {
                        tMapView.removeMarkerItem2(facilityList.getArrayList().get(i).getMarker().getID());
                    }
                }
            });
        }
        else{
            tMapData.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint, endPoint, new TMapData.FindPathDataListenerCallback() {
                @Override
                public void onFindPathData(TMapPolyLine polyLine) {
                    polyLine.setID("result");
                    tMapView = facilityList.gettMapView();
                    tMapView.addTMapPath(polyLine);
                    int mSize = facilityList.getArrayList().size();
                    for (int i = 0; i < mSize; i++) {
                        tMapView.removeMarkerItem2(facilityList.getArrayList().get(i).getMarker().getID());
                    }
                }
            });
        }

    }

    public void getPathDocument(TMapPoint startPoint, TMapPoint endPoint, int isCar){
        TMapData tMapData = new TMapData();
        if(isCar==1) {
            tMapData.findPathDataAllType(TMapData.TMapPathType.CAR_PATH, startPoint, endPoint, new TMapData.FindPathDataAllListenerCallback() {
                @Override
                public void onFindPathDataAll(Document document) {
                    RouteAdapter adapter = new RouteAdapter();
                    adapter.setIsCar(1);
                    Element root = document.getDocumentElement();
                    NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");
                    Log.d("NodeList", nodeListPlacemark + "");
                    for (int i = 0; i < nodeListPlacemark.getLength(); i++) {
                        NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                        Log.d("Item", nodeListPlacemarkItem + "");
                        for (int j = 0; j < nodeListPlacemarkItem.getLength(); j++) {
                            if (nodeListPlacemarkItem.item(j).getNodeName().equals("description")) {
                                adapter.addItem(new RouteItem(nodeListPlacemarkItem.item(j).getTextContent().trim()));
                                Log.d("debug", nodeListPlacemarkItem.item(j).getTextContent().trim());
                            }
                        }
                    }


                    fm = getSupportFragmentManager();
                    fm.popBackStack();
                    Fragment Route = new Route(adapter);
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.enter_to_bottom, R.anim.enter_from_bottom, R.anim.enter_to_bottom);
                    transaction.add(R.id.info, Route);
                    transaction.commit();
                    transaction.addToBackStack(null);
                }
            });
        }
        else{
            tMapData.findPathDataAllType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint, endPoint, new TMapData.FindPathDataAllListenerCallback() {
                @Override
                public void onFindPathDataAll(Document document) {
                    RouteAdapter adapter = new RouteAdapter();
                    Element root = document.getDocumentElement();
                    NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");
                    Log.d("NodeList", nodeListPlacemark + "");
                    for (int i = 0; i < nodeListPlacemark.getLength(); i++) {
                        NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                        Log.d("Item", nodeListPlacemarkItem + "");
                        for (int j = 0; j < nodeListPlacemarkItem.getLength(); j++) {
                            if (nodeListPlacemarkItem.item(j).getNodeName().equals("description")) {
                                adapter.addItem(new RouteItem(nodeListPlacemarkItem.item(j).getTextContent().trim()));
                                Log.d("debug", nodeListPlacemarkItem.item(j).getTextContent().trim());
                            }
                        }
                    }
                    fm = facilityList.getFm();
                    fm.popBackStack();
                    Fragment Route = new Route(adapter);
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.enter_to_bottom, R.anim.enter_from_bottom, R.anim.enter_to_bottom);
                    transaction.add(R.id.info, Route);
                    transaction.commit();
                    transaction.addToBackStack(null);
                }
            });
        }
    }

    public void invalidRoute(int isCar){
        getPath(new TMapPoint(37.503149,126.952264),
                new TMapPoint(Double.parseDouble(selectedFacility.getLat()),Double.parseDouble(selectedFacility.getLng())),isCar);
        getPathDocument(new TMapPoint(37.503149,126.952264),
                new TMapPoint(Double.parseDouble(selectedFacility.getLat()),Double.parseDouble(selectedFacility.getLng())),isCar);
        setBarMode(1);
        invalidateOptionsMenu();
    }
}
