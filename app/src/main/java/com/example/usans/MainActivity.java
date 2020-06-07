package com.example.usans;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.usans.Activity.AddSansAcitivity;
import com.example.usans.Activity.BoardActivity;
import com.example.usans.Activity.DetailActivity;
import com.example.usans.Activity.ImageActivity;
import com.example.usans.Activity.RoutineActivity;
import com.example.usans.Adapter.RouteAdapter;
import com.example.usans.CustomLayout.CustomActionBar;
import com.example.usans.CustomLayout.Route;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.Data.RouteItem;
import com.example.usans.SceneFragment.HomeFragment;
import com.example.usans.SceneFragment.ListFragment;
import com.example.usans.SceneFragment.MypageFragment;
import com.example.usans.SceneFragment.RegFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int LOGIN_IS_REQUIRED = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_IMAGE = 2;

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
    private LatLng userLocation;
    private int goToWrite=0;

    private String[] navItems = {"500m", "1km", "2km", "5km", "10km"};
    ListView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_main);
        facilityList = (FacilityList) getApplication();
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        userLocation = new LatLng(37.503149, 126.952264);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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

        navView = findViewById(R.id.nav_view);
        navView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        navView.setOnItemClickListener(new DrawerItemClickListener());

//        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, heartFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, mypageFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, regFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, homeFragment).commit();
        setBackground(0);
        facilityList.setMainActivity(this);
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

    public void moveToAddSans(TMapPoint centerPoint) {
        Intent intent = new Intent(this, AddSansAcitivity.class);
        intent.putExtra("lat",centerPoint.getLatitude());
        intent.putExtra("lng",centerPoint.getLongitude());
        startActivityForResult(intent,10000);
    }

    public void moveToDetail(Facility facility) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("facility", facility);
        startActivityForResult(intent,10001);
    }

    @Override
    public void onClick(View v) {
        facilityList.setGoToComment(-1);
        facilityList.setGoToBoard(-1);
        switch (v.getId()) {
            case R.id.homebutton:
                setBackground(0);
                setFrag(0);
                setBarMode(0);
                invalidateOptionsMenu();
                break;
            case R.id.listbutton:
                setBackground(1);
                setFrag(1);
                setBarMode(2);
                invalidateOptionsMenu();
                listFragment.updateAdapter();
                break;
            case R.id.regbutton:
                setBackground(2);
                setFrag(2);
                setBarMode(2);
                invalidateOptionsMenu();
                break;
            case R.id.mypagebutton:
                setBackground(3);
                setFrag(3);
                setBarMode(2);
                invalidateOptionsMenu();
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
                    if (homeFragment.showAddMarkerButton())
                        item.setIcon(R.drawable.cancel);
                    else
                        item.setIcon(R.drawable.plus);
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
                    for(int i =0;i<6;i++){
                        tMapView.addMarkerItem2(String.valueOf(90000+i),facilityList.getMountainList().get(i).getMarker());
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
        } else {
            getMenuInflater().inflate(R.menu.main2, menu);
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

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(listFragment).commit();

                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(mypageFragment).commit();
                getSupportFragmentManager().beginTransaction().show(regFragment).commit();

                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                homeFragment.addMarkerButtom.setVisibility(View.INVISIBLE);
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().hide(homeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(listFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(regFragment).commit();
                getSupportFragmentManager().beginTransaction().show(mypageFragment).commit();

                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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

    public void getPath(TMapPoint startPoint, final TMapPoint endPoint, final int isCar) {
        TMapData tMapData = new TMapData();
        TMapData.TMapPathType pathType;
        if (isCar == 1) pathType = TMapData.TMapPathType.CAR_PATH;
        else pathType = TMapData.TMapPathType.PEDESTRIAN_PATH;
        tMapData.findPathDataWithType(pathType, startPoint, endPoint, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine polyLine) {
                if(isCar ==1){
                    tMapView.removeAllTMapPolyLine();
                }
                polyLine.setID("result");
                facilityList.setPolyline(polyLine);
                tMapView = facilityList.gettMapView();
                tMapView.addTMapPath(polyLine);
                int mSize = facilityList.getArrayList().size();
                for (int i = 0; i < mSize; i++) {
                    tMapView.removeMarkerItem2(facilityList.getArrayList().get(i).getMarker().getID());
                }
                for(int i =0;i<6;i++){
                    tMapView.removeMarkerItem2(facilityList.getMountainList().get(i).getMarker().getID());
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
                MarkerOverlay markerItem2 = new MarkerOverlay(getApplicationContext(), "hi", "hi", null, tMapView);

                MountainPathDrawer mountainPathDrawer = new MountainPathDrawer();
                mountainPathDrawer.drawMountainPath(tMapView,new TMapPoint(endPoint.getLatitude(), endPoint.getLongitude()),getApplicationContext(),facilityList);


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

    class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Log.e("시발", "클릭");
            tMapView = facilityList.gettMapView();
            double range = 0;
            switch (position) {
                case 0:
                    range = 500;
                    break;
                case 1:
                    range = 1000;
                    break;
                case 2:
                    range = 2000;
                    break;
                case 3:
                    range = 5000;
                    break;
                case 4:
                    range = 10000;
                    break;
            }
            for (Facility facility : facilityList.getArrayList()) {
                if (facility.getDistance() <= range)
                    tMapView.addMarkerItem2(facility.getMarker().getID(), facility.getMarker());
                else
                    tMapView.removeMarkerItem2(facility.getMarker().getID());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000) {
            if (resultCode == 10101) {
                String url = "http://3.34.18.171:8000/api/Sansuzang";
                NetworkTaskPlus networkTask = new NetworkTaskPlus(url, null);
                networkTask.execute();
            }
        }
        else if (requestCode ==10001){
            if (resultCode == LOGIN_IS_REQUIRED) {
                setBackground(3);
                setFrag(3);
                String id = data.getStringExtra("facilityID");
                facilityList.setGoToComment(Integer.parseInt(id));
                Log.d("id",id);
            }
        }
    }

    public class NetworkTaskPlus extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;
        public NetworkTaskPlus(String url, ContentValues values) {
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
                if (s != null) {
                    JSONArray jsArr = new JSONArray(s); //1539
                    int invalidLen = jsArr.length();

                    while(invalidLen-homeFragment.jsLen!=0){
                        parseJS2(jsArr,homeFragment.jsLen);
                        homeFragment.jsLen++;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void parseJS2(JSONArray jsonArray, int index){
            Facility facility = new Facility();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                facility.setId(jsonObject.getString("id"));
                facility.setName(jsonObject.getString("name"));
                facility.setAddress(jsonObject.getString("address"));
                facility.setLat(jsonObject.getString("lat"));
                facility.setLng(jsonObject.getString("lon"));
                facility.setDistance(homeFragment.getDistance(userLocation,
                        new LatLng(Double.parseDouble(jsonObject.getString("lat")),
                                Double.parseDouble(jsonObject.getString("lon")))));

                facilityList.getArrayList().add(facility);
                Bitmap bitmap;
                MarkerOverlay markerItem1 = new MarkerOverlay(getApplicationContext(),"hi","hi",facilityList.getFm(),tMapView);
                TMapPoint tMapPoint1 = new TMapPoint(Double.parseDouble(facility.getLat()),Double.parseDouble(facility.getLng()));
                markerItem1.setTMapPoint( tMapPoint1 );
                markerItem1.setID(String.valueOf(facilityList.getArrayList().size()-1));
                if(Integer.parseInt(facility.getId())>=10005) {
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.marker_icon_red);
                }
                else{
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.marker_icon_blue);
                }
                markerItem1.setIcon(bitmap);
                markerItem1.setIcon(homeFragment.resizeBitmap(bitmap, 200));
                markerItem1.setPosition(0.5f, 0.8f);
                homeFragment.gettMapView().addMarkerItem2(markerItem1.getID(), markerItem1);
                facilityList.getArrayList().get(facilityList.getArrayList().size()-1).setMarker(markerItem1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

