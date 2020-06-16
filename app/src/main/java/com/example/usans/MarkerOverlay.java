package com.example.usans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.example.usans.CustomLayout.Info;
import com.example.usans.Data.Facility;
import com.example.usans.Data.FacilityList;
import com.google.android.gms.maps.model.LatLng;
import com.skt.Tmap.TMapMarkerItem2;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MarkerOverlay extends TMapMarkerItem2 {

    private DisplayMetrics dm = null;
    private Context mContext = null;
    private BalloonOverlayView balloonView = null;
    private int mAnimationCount = 0;
    private FacilityList facilityList;
    private FragmentManager fm;
    private TMapView tMapView;

    @Override
    public Bitmap getIcon() {
        return super.getIcon();
    }

    @Override
    public void setIcon(Bitmap bitmap) {
        super.setIcon(bitmap);
    }

    @Override
    public void setTMapPoint(TMapPoint point) {
        super.setTMapPoint(point);
    }

    @Override
    public TMapPoint getTMapPoint() {
        return super.getTMapPoint();
    }

    @Override
    public void setPosition(float dx, float dy) {
        super.setPosition(dx, dy);
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFacilityList(FacilityList facilityList) {
        this.facilityList = facilityList;
    }

    /**
     * 풍선뷰 영역을 설정한다.
     */
    @Override
    public void setCalloutRect(Rect rect) {
        super.setCalloutRect(rect);
    }

    public MarkerOverlay(Context context, String labelName, String id,FragmentManager fm,TMapView tMapView) {
        this.mContext = context;
        this.fm = fm;
        this.tMapView = tMapView;
        facilityList = (FacilityList) context;
        dm = new DisplayMetrics();
        WindowManager wmgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wmgr.getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public void draw(Canvas canvas, TMapView mapView, boolean showCallout) {
        int x = mapView.getRotatedMapXForPoint(getTMapPoint().getLatitude(), getTMapPoint().getLongitude());
        int y = mapView.getRotatedMapYForPoint(getTMapPoint().getLatitude(), getTMapPoint().getLongitude());

        canvas.save();
        canvas.rotate(-mapView.getRotate(), mapView.getCenterPointX(), mapView.getCenterPointY());

        float xPos = getPositionX();
        float yPos = getPositionY();

        int nPos_x, nPos_y;

        int nMarkerIconWidth = 0;
        int nMarkerIconHeight = 0;
        int marginX = 0;
        int marginY = 0;

        nMarkerIconWidth = getIcon().getWidth();
        nMarkerIconHeight = getIcon().getHeight();

        nPos_x = (int) (xPos * nMarkerIconWidth);
        nPos_y = (int) (yPos * nMarkerIconHeight);

        if(nPos_x == 0) {
            marginX = nMarkerIconWidth / 2;
        } else {
            marginX = nPos_x;
        }

        if(nPos_y == 0) {
            marginY = nMarkerIconHeight / 2;
        } else {
            marginY = nPos_y;
        }

        canvas.translate(x - marginX, y - marginY);
        canvas.drawBitmap(getIcon(), 0, 0, null);
        canvas.restore();
    }

    public boolean onSingleTapUp(PointF point, TMapView mapView) {
        if(!getID().equals("temp")) {
            if(Integer.parseInt(getID())<90000) {
                fm.popBackStack();
                Facility facility;

                facility = new Facility(facilityList.getArrayList().get(Integer.parseInt(getID())));
                Fragment inf = new Info(facility, tMapView);

                FragmentTransaction transaction = fm.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.enter_to_bottom, R.anim.enter_from_bottom, R.anim.enter_to_bottom);
                transaction.add(R.id.info, inf);
                transaction.commit();
                transaction.addToBackStack(null);
            }
            else{
                fm.popBackStack();
                Facility facility = facilityList.getMountainList().get(Integer.parseInt(getID())-90000);
                tMapView.setCenterPoint(facilityList.getUserLocation().longitude,facilityList.getUserLocation().latitude,true);
                MainActivity mainActivity = facilityList.getMainActivity();
                mainActivity.setSelectedFacility(facility);
                mainActivity.invalidRoute(0);
                //MountainPathDrawer mountainPathDrawer = new MountainPathDrawer(); 여깁니다
                //mountainPathDrawer.drawMountainPath(tMapView,new TMapPoint(Double.parseDouble(facility.getLat()), Double.parseDouble(facility.getLng())),mContext,facilityList);

            }

        }
        return false;
    }
}