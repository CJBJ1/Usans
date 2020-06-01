package com.example.usans;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.usans.CustomLayout.CustomActionBar;
import com.example.usans.CustomLayout.Info;
import com.example.usans.CustomLayout.Route;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.Data.RouteItem;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public HomeFragment homeFragment;
    public CustomActionBar ca;
    Button homeButton, listButton, regButton, mypageButton;
    ListFragment listFragment;
    MypageFragment mypageFragment;
    RegFragment regFragment;
    RelativeLayout homeLayout, listLayout, regLayout, mypageLayout, heartLayout;
    int barMode = 0;
    private FacilityList facilityList;
    private Facility selectedFacility;
    private DrawerLayout mDrawerLayout;
    private TMapView tMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_main);
        facilityList = (FacilityList) getApplication();
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ca = new CustomActionBar(this, getSupportActionBar());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        homeButton = (Button) findViewById(R.id.homebutton);
        listButton = (Button) findViewById(R.id.listbutton);
        regButton = (Button) findViewById(R.id.regbutton);
        mypageButton = (Button) findViewById(R.id.mypagebutton);

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

    public void moveToAddSans(double lat,double lng) {
        Intent intent = new Intent(this, AddSansAcitivity.class);
        intent.putExtra("lat",lat);
        intent.putExtra("lng",lng);
        startActivity(intent);
    }

    public void moveToDetail(Facility facility) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("facility", facility);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        if (barMode == 0) {
            switch (item.getItemId()) {
                case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;
                }
                case R.id.menu_add: {
                    homeFragment.showAddMarkerButton();
                }
            }
        } else if (barMode == 1) {
            switch (item.getItemId()) {
                case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                    onBackPressed();
                    return true;
                }
                case R.id.routeBar_walk: {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                        onBackPressed();
                    }
                    invalidRoute(0);
                    return true;
                }
                case R.id.routeBar_car: {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                        onBackPressed();
                    }
                    invalidRoute(1);
                    return true;
                }
                case R.id.routeBar_ok: {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                        onBackPressed();
                    }
                    setBarMode(0);
                    invalidateOptionsMenu();
                    tMapView.removeMarkerItem2("temp");
                    int size = facilityList.getArrayList().size();
                    TMapView tMapView = facilityList.gettMapView();
                    for (int i = 0; i < size; i++) {
                        tMapView.addMarkerItem2(String.valueOf(i), facilityList.getArrayList().get(i).getMarker());
                    }
                    tMapView.removeAllTMapPolyLine();
                    tMapView.removeTMapPath();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (barMode == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        } else if (barMode == 1) {
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

    public int getBarMode() {
        return barMode;
    }

    public void setBarMode(int barMode) {
        this.barMode = barMode;
    }

    public Facility getSelectedFacility() {
        return selectedFacility;
    }

    public void setSelectedFacility(Facility selectedFacility) {
        this.selectedFacility = selectedFacility;
    }

    public void getPath(TMapPoint startPoint, TMapPoint endPoint, int isCar) {
        TMapData tMapData = new TMapData();
        TMapData.TMapPathType pathType;
        if (isCar == 1) pathType = TMapData.TMapPathType.CAR_PATH;
        else pathType = TMapData.TMapPathType.PEDESTRIAN_PATH;
        tMapData.findPathDataWithType(pathType, startPoint, endPoint, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine polyLine) {
                polyLine.setID("result");
                tMapView = facilityList.gettMapView();
                tMapView.addTMapPath(polyLine);
                int mSize = facilityList.getArrayList().size();
                for (int i = 0; i < mSize; i++) {
                    tMapView.removeMarkerItem2(facilityList.getArrayList().get(i).getMarker().getID());
                }
                MarkerOverlay markerItem1 = new MarkerOverlay(getApplicationContext(), "hi", "hi", null, tMapView);
                TMapPoint tMapPoint1 = new TMapPoint(Double.parseDouble(selectedFacility.getLat()), Double.parseDouble(selectedFacility.getLng()));
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.marker_icon_blue);
                markerItem1.setIcon(bitmap);
                markerItem1.setTMapPoint(tMapPoint1);
                markerItem1.setID("temp");
                markerItem1.setPosition(0.5f, 0.8f);
                markerItem1.setIcon(homeFragment.resizeBitmap(bitmap, 200));
                tMapView.addMarkerItem2(markerItem1.getID(), markerItem1);
            }
        });
    }

    public void getPathDocument(TMapPoint startPoint, TMapPoint endPoint, int isCar) {
        TMapData tMapData = new TMapData();
        final RouteAdapter adapter = new RouteAdapter();
        TMapData.TMapPathType pathType;
        if (isCar == 1) {
            adapter.setIsCar(1);
            pathType = TMapData.TMapPathType.CAR_PATH;
        } else {
            pathType = TMapData.TMapPathType.PEDESTRIAN_PATH;
        }
        tMapData.findPathDataAllType(pathType, startPoint, endPoint, new TMapData.FindPathDataAllListenerCallback() {
            @Override
            public void onFindPathDataAll(Document document) {
                Element root = document.getDocumentElement();
                NodeList nodeListPlacemark = root.getElementsByTagName("Placemark");
                for (int i = 0; i < nodeListPlacemark.getLength(); i++) {
                    NodeList nodeListPlacemarkItem = nodeListPlacemark.item(i).getChildNodes();
                    for (int j = 0; j < nodeListPlacemarkItem.getLength(); j++) {
                        if (nodeListPlacemarkItem.item(j).getNodeName().equals("description")) {
                            adapter.addItem(new RouteItem(nodeListPlacemarkItem.item(j).getTextContent().trim()));
                        }
                    }
                }
                Fragment Route = new Route(adapter);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.enter_to_bottom, R.anim.enter_from_bottom, R.anim.enter_to_bottom);
                transaction.add(R.id.info, Route);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    public void invalidRoute(int isCar) {
        getPath(new TMapPoint(37.503149, 126.952264),
                new TMapPoint(Double.parseDouble(selectedFacility.getLat()), Double.parseDouble(selectedFacility.getLng())), isCar);
        getPathDocument(new TMapPoint(37.503149, 126.952264),
                new TMapPoint(Double.parseDouble(selectedFacility.getLat()), Double.parseDouble(selectedFacility.getLng())), isCar);
        setBarMode(1);
        invalidateOptionsMenu();
    }

    public void loadGpxData(XmlPullParser parser, InputStream gpxIn) throws XmlPullParserException, IOException {
        tMapView = facilityList.gettMapView();
        parser.setInput(gpxIn, null);
        parser.nextTag();
        int id = 0;
        ArrayList<TMapPoint> tMapPoints = new ArrayList<TMapPoint>();

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            if (parser.getName().equals("trk")) {
                if (tMapPoints.size() != 0) {
                    TMapPolyLine tMapPolyLine = new TMapPolyLine();
                    tMapPolyLine.setLineColor(Color.BLUE);
                    tMapPolyLine.setLineWidth(2);
                    for (int i = 0; i < tMapPoints.size(); i++) {
                        tMapPolyLine.addLinePoint(tMapPoints.get(i));
                    }
                    tMapView.addTMapPolyLine(String.valueOf(id), tMapPolyLine);
                    id++;
                }
                tMapPoints = new ArrayList<>();
            }

            if (parser.getName().equals("trkpt")) {
                tMapPoints.add(new TMapPoint(
                        Double.valueOf(parser.getAttributeValue(null, "lat")),
                        Double.valueOf(parser.getAttributeValue(null, "lon"))));
            }
        }
        TMapPolyLine tMapPolyLine = new TMapPolyLine();
        tMapPolyLine.setLineColor(Color.BLUE);
        tMapPolyLine.setLineWidth(2);
        for (int i = 0; i < tMapPoints.size(); i++) {
            tMapPolyLine.addLinePoint(tMapPoints.get(i));
        }
        tMapView.addTMapPolyLine(String.valueOf(++id), tMapPolyLine);
    }
}
