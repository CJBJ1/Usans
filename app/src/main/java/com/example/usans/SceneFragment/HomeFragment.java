package com.example.usans.SceneFragment;

import android.Manifest;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;
import com.example.usans.CustomLayout.Info;
import com.example.usans.CustomLayout.Recommend;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.example.usans.GpsTracker;
import com.example.usans.MainActivity;
import com.example.usans.MarkerOverlay;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.skt.Tmap.TMapMarkerItem2;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {
    TMapView tMapView;
    private View view;
    private FragmentManager fm;
    private FragmentManager infoFm;
    private SupportMapFragment mapFragment;
    private FacilityList facilityList;
    private JSONArray jsArr;
    private GoogleMap mMap;
    private LatLng userLocation;

    public ImageView addMarker;
    public LinearLayout zoomLayout;
    public Button addMarkerButtom, zoomPlus, zoomMinus;
    public Button sansNavigationStartButton;
    private Button gpsButton;
    private List<LatLng> latLngs;
    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        userLocation = new LatLng(37.503149, 126.952264);
        facilityList = (FacilityList)getActivity().getApplicationContext();
        fm = getFragmentManager();
        infoFm = getFragmentManager();

        LinearLayout linearLayoutTmap = (LinearLayout)view.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getActivity().getApplicationContext());

        tMapView.setSKTMapApiKey( "l7xxa365f1c5c3254bc19fd5f6b6442b15e5" );
        linearLayoutTmap.addView( tMapView );

        String url = "http://3.34.18.171:8000/api/Sansuzang";
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        addMarker = view.findViewById(R.id.marker_image);
        addMarkerButtom = view.findViewById(R.id.add_marker_button);
        zoomPlus = view.findViewById(R.id.zoom_plus);
        zoomMinus = view.findViewById(R.id.zoom_minus);
        sansNavigationStartButton = view.findViewById(R.id.sans_navigation_start_button);

        zoomPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.setZoomLevel(tMapView.getZoomLevel()+1);
            }
        });
        zoomMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMapView.setZoomLevel(tMapView.getZoomLevel()-1);
            }
        });
        zoomLayout = view.findViewById(R.id.zoom_layout);
        zoomLayout.setVisibility(View.VISIBLE);

        gpsButton = view.findViewById(R.id.gps_button);

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(getActivity().getApplicationContext());
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude()*(-1);

                String address = getCurrentAddress(latitude, longitude);
                Toast.makeText(getActivity().getApplicationContext(), "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                tMapView.setCenterPoint(longitude,latitude,true);
            }
        });

        facilityList.setFm(fm);

        return view;
    }

    public void showRecommend(String machines){
        infoFm.popBackStack();

        Recommend recommend = new Recommend();
        recommend.machines = machines;
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

    public void popChild() {
        FragmentManager ft = getChildFragmentManager();
        ft.popBackStack();
    }

    public void setMap(){
        ArrayList<Facility> list = facilityList.getArrayList();
        int size = facilityList.getArrayList().size();
        for(int i = 0;i<size;i++){
            Facility facility = list.get(i);
            MarkerOverlay markerItem1 = new MarkerOverlay(getActivity().getApplicationContext(),"hi","hi",fm,tMapView);
            TMapPoint tMapPoint1 = new TMapPoint(Double.parseDouble(facility.getLat()),Double.parseDouble(facility.getLng())); // SKT타워
            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), R.drawable.marker_icon_blue);
            markerItem1.setIcon(bitmap);
            markerItem1.setTMapPoint( tMapPoint1 );
            markerItem1.setID(String.valueOf(i));
            markerItem1.setIcon(resizeBitmap(bitmap, 200));
            markerItem1.setPosition(0.5f, 0.8f);
            tMapView.addMarkerItem2(markerItem1.getID(), markerItem1);
            facilityList.getArrayList().get(i).setMarker(markerItem1);
        }
        tMapView.setCenterPoint( 126.985302, 37.570841 );
        tMapView.setOnMarkerClickEvent(new TMapView.OnCalloutMarker2ClickCallback() {
            @Override
            public void onCalloutMarker2ClickEvent(String s, TMapMarkerItem2 tMapMarkerItem2) {
                Log.d("클릭","클릭");
            }
        });

        facilityList.settMapView(tMapView);
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
                Log.d("결과",s);
                addSans();
                int index=0;
                if (s != null) {
                    jsArr = new JSONArray(s);
                    while (index != 10) {
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

    public Bitmap resizeBitmap(Bitmap original, int width) {
        int resizeWidth = width;

        double aspectRatio = (double) original.getHeight() / (double) original.getWidth();
        int targetHeight = (int) (resizeWidth * aspectRatio);

        Bitmap result = Bitmap.createScaledBitmap(original, resizeWidth, targetHeight, false);
        if( result != original) {
            original.recycle();
        }
        return result;
    }

    public String getCurrentAddress( double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getActivity().getApplicationContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getActivity().getApplicationContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

    public void loadGpxData(XmlPullParser parser, InputStream gpxIn)
            throws XmlPullParserException, IOException {
        parser.setInput(gpxIn, null);
        parser.nextTag();
        int id =0;
        ArrayList<TMapPoint> tMapPoints = new ArrayList<TMapPoint>();

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName().equals("trk")){
                if(tMapPoints.size()!=0){
                    TMapPolyLine tMapPolyLine = new TMapPolyLine();
                    tMapPolyLine.setLineColor(Color.BLUE);
                    tMapPolyLine.setLineWidth(2);
                    for( int i=0; i<tMapPoints.size(); i++ ) {
                        tMapPolyLine.addLinePoint( tMapPoints.get(i) );
                    }
                    tMapView.addTMapPolyLine(String.valueOf(id), tMapPolyLine);
                    id++;
                }

                tMapPoints = new ArrayList<TMapPoint>();
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
        for( int i=0; i<tMapPoints.size(); i++ ) {
            tMapPolyLine.addLinePoint( tMapPoints.get(i) );
        }
        tMapView.addTMapPolyLine(String.valueOf(++id), tMapPolyLine);
    }

    public void addSans(){
        Facility facility = new Facility("10000", "분당중앙공원", "경기 성남시 분당구 성남대로 550",
                new String[]{"https://img.theqoo.net/img/cipEd.jpg",
                        "https://img.theqoo.net/img/JiUjC.jpg",
                        "https://img.theqoo.net/img/BgmcT.jpg",
                        "https://img.theqoo.net/img/kBWWT.jpg",
                        "https://img.theqoo.net/imgF/jvVtA.jpg",
                        "https://img.theqoo.net/img/alvzg.png"}, "스쿼트랙 레그익스텐션머신 레그컬머신 벤치프레스", "37.376260", "127.124842", 4 ,
                getDistance(userLocation,
                        new LatLng(37.376260, 127.124842))
        );
        facilityList.getArrayList().add(facility);
        facility = new Facility("10001","남산체육관","서울 용산구 소월로 272",
                new String[]{"https://post-phinf.pstatic.net/MjAxNzA0MTFfMjIg/MDAxNDkxOTAxNTQ0MDIx.3lpcdB_DxIL00Bnpj4bfTooCYO9Q-fISZ-is70YGnxwg.O04eaSQVvPviZkc2kyUeumy6asVlzgRATcIYvBCblWQg.JPEG/KakaoTalk_20170411_180400451.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAxNzA0MTFfMjQy/MDAxNDkxOTAxODIyMDk1.0ZSo5lUtFG_iNZZlz3N5tvfwNr3JvoUy-8_wtpfgS00g.odU29pGf2YmD5MJ_lgAcBBnfQzdhdiV7Ab0lAnwlHWEg.JPEG/IMG_6107.JPG?type=w1200",
                        "https://post-phinf.pstatic.net/MjAxNzA0MTFfMiAg/MDAxNDkxOTAxODIyMTU0.Hk7z3DXkTSVuaGU_n_50axKmO9SMvItHa45psiyQPXMg.XxAp07EMaBA0W5OFGq-CwekcuDvKMuoUglp3JdWjzRAg.JPEG/IMG_6111.JPG?type=w1200",
                        "https://post-phinf.pstatic.net/MjAxNzA0MTFfMjUy/MDAxNDkxOTAxOTM1NTQ1.S4jp6qgjbQfSzLkIsyKs7bmmbfJ2EB2DCyq3KnMOmGUg.gNu-77vMOyebbRwkrV2m2Ni5Ju_t6KxZdKvKcoiZ4nQg.JPEG/IMG_6105.JPG?type=w1200",
                        "https://post-phinf.pstatic.net/MjAxNzA0MTFfMjgz/MDAxNDkxOTAxODU5NTg1.r0qXHuL2axoqG3VfuAF-c96x5dTro9jXF5_J8LrJB-Ag.UyVmmVmAGMewH5mqLhkY7-MtQaWoua3TR8mPF7N3Liwg.JPEG/KakaoTalk_20170411_180516595.jpg?type=w1200"},"벤치프레스 스탠딩레그컬머신 레그익스텐션머신 체스트프레스머신 버터플라이머신 철봉", "37.542769", "126.992699", 4 ,
                getDistance(userLocation,
                        new LatLng(37.542769, 126.992699)));
        facilityList.getArrayList().add(facility);

        facility = new Facility("10002","관악산 운동시설","서울 관악구 대학동 산28-7",
                new String[]{"https://postfiles.pstatic.net/MjAyMDA0MTFfNjgg/MDAxNTg2NTk1NDc1NDgx.XX7HZPu4V01tEMCNL-5SpTucuuyH4_QGHXDblqVGGZYg.06Crs3nTfDkWQ6IVyjm9Z9yejniYOYIBtsiQSi7ILiog.JPEG.lovehun96/1586595476219.jpg?type=w580",
                "https://postfiles.pstatic.net/MjAyMDA0MTFfMTE0/MDAxNTg2NTk1NDc2MzM0.ox5mtOW6lsJvxG4ppGaTtgQJwSWSGitJSBZ_f7QladMg.umd0kotG-cYZEmZXPUrXCkYuERYrZDiCmDd9iDgz5QQg.JPEG.lovehun96/1586595477014.jpg?type=w580",
                "https://postfiles.pstatic.net/MjAyMDA0MTFfMjcz/MDAxNTg2NTk1NDgwODcw.jjSg9KsV5r4cmhl3EQg-5mXUBGd2SF17roh6w1GHI1gg.cZgJLh8HWesc7Gq47VPO5GL-JMdOL0uHvXgWl0Rs3N4g.JPEG.lovehun96/1586595481621.jpg?type=w580",
                "https://postfiles.pstatic.net/MjAyMDA0MTFfMjAg/MDAxNTg2NTk1NDgwMDk2.wUOIOLSQzEAqIPuHkG4SkrZwwyo3WlM4CCo6yLBsNBUg.kOH-98BIQ1TvHz6QZOFr5IY-XjfoC4PzaiyThLc6nPwg.JPEG.lovehun96/1586595480857.jpg?type=w580"},"벤치프레스 랫풀다운머신 체스트프레스머신 버터플라이머신", "37.445262", "126.975757", 4 ,
                getDistance(userLocation,
                        new LatLng(37.445262, 126.975757)));
        facilityList.getArrayList().add(facility);

        facility = new Facility("10003","봉화산 운동시설","서울 중랑구 묵동 산46-1",
                new String[]{"https://mblogthumb-phinf.pstatic.net/MjAxOTExMjJfMTQw/MDAxNTc0NDA5NzYyOTk3.qH6PsnyjojmBxFWJcv7vhjTM5iNV5MQoUh29ax-dFkMg.g5v88Tzy8vudGyGykTCTEaya5CPpMOtIO9jNGaUBZ38g.JPEG.mission8605/20191110_081334.jpg?type=w800",
                "https://mblogthumb-phinf.pstatic.net/MjAxOTExMjJfMjQ5/MDAxNTc0NDA5NzYyMjM5._vbdW08G2SGvOZOzahCi0PRlChgGddFaxdY9xKElQRAg.zRJt9YrYENZnuQg9PqkpoQdByiPGeZSDX5FLe-34NIQg.JPEG.mission8605/20191110_081329.jpg?type=w800",
                "https://mblogthumb-phinf.pstatic.net/MjAxOTExMjJfMjEg/MDAxNTc0NDA5NzYzNDQx.QkzIZO6T0g41v4lfKLIRkleMLjqIto8zHxpe8zfRpesg.YibJbTP6ydEdqc4aXoBGUShsw5umDFoT4crTBD-jguAg.JPEG.mission8605/20191110_081323.jpg?type=w800",
                "https://mblogthumb-phinf.pstatic.net/MjAxOTExMjJfNDQg/MDAxNTc0NDA5NzY2MDIy.54y-3fKyO92e84ySH8Hgtr1zDuW3uoQNm5kl4k1nH-Mg.vago4ADcYq_ThbwEqorvMpl3Sd-vbl2cTUruGEROob0g.JPEG.mission8605/20191110_081311.jpg?type=w800",
                "https://mblogthumb-phinf.pstatic.net/MjAxOTExMjJfNzIg/MDAxNTc0NDA5NzYxNzky.emFDxaLqt0FVVpHdRXSefPpjODZGsHJJr5dDZEqZgWQg.--Avz-6-tcrJoUtoAW-CC94lZb6dHMIlNUgdlw351_gg.JPEG.mission8605/20191110_081252.jpg?type=w800"},"스미스머신 암컬머신 숄더프레스머신 랫풀다운머신 버터플라이머신 레그프레스머신 " +
                "허리돌리기 철봉", "37.608263", "127.086944", 4 ,
                getDistance(userLocation,
                        new LatLng(37.608263, 127.086944)));
        facilityList.getArrayList().add(facility);
        facility = new Facility("10004","고구동산 체력단련장","서울 동작구 본동",
                new String[]{"https://lh3.googleusercontent.com/proxy/PJ3fx5ZH4tFoTFktBwLAeCIABke909Rn0uQuV8efUvxu2rcoslB6ELzr5DQzygYy3abcIk1_AI6jTx6UanE8RMJrcnQlng04ag7zFxUI0n8", "http://ideeaa.net/wordpress/wp-content/uploads/2015/07/g3.jpg"},
                "벤치프레스 스탠딩레그컬머신 랫풀다운 레그익스텐션머신 체스트프레스머신 버터플라이머신 철봉",
                "37.508181", "126.954435", 5 ,
                getDistance(userLocation,
                        new LatLng(37.508181, 126.954435)));
        facilityList.getArrayList().add(facility);
        facility = new Facility("10005","고구동산 테스트","서울 동작구 본동",
                new String[] {},
                "철봉",
                "37.507585", "126.954684", 1 ,
                getDistance(userLocation,
                        new LatLng(37.507585, 126.954684)));
        facilityList.getArrayList().add(facility);
    }
}
