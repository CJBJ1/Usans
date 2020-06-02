package com.example.usans;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MountainPathDrawer {
    private TMapView tMapView;

    public void drawMountainPath(TMapView tMapView, TMapPoint endPoint,Context context){
        this.tMapView = tMapView;
        String fileName = "";
        double lat = endPoint.getLatitude();
        double lng = endPoint.getLongitude();
        Log.d("담아",endPoint.getLatitude() + " " + endPoint.getLongitude());

        if(lat<=37.46388192994252 &&
        lng>=126.96077585220337 &&
        lat>=37.41831664893894 &&
        lng<=126.99304819107056){
            fileName = "관악산.gpx";
        }

        else if(lat<=37.464401396417195 &&
        lng>=126.93318128585815&&
        lat>=37.436055395618304&&
        lng<=126.94811582565308){
            fileName = "관악산왼편.gpx";
        }

        else if(lat<=37.630445204076175&&
        lng>=126.91923379898071&&
        lat>=37.62646856436861&&
        lng<=126.92925453186035){
            fileName = "갈현근린공원.gpx";
        }

        else if(lat<=37.628218991822514&&
        lng>=126.92925453186035&&
        lat>=37.6227636402104&&
        lng<=126.93747282028198){
            fileName = "갈현근린공원2지구.gpx";
        }

        else if(lat<=337.58697952946763&&
        lng>=126.79965019226074&&
        lat>=37.57434473339113&&
        lng<=126.81904792785645){
            fileName = "개화산.gpx";
        }

        else if(lat<=37.58973407241862&&
        lng>=126.93790197372437&&
        lat>=37.58398682406178&&
        lng<=126.94311618804932){
            fileName = "고은산.gpx";
        }
        else if(lat<=37.598991650188076&&
        lng>=126.94929599761963&&
        lat>=37.57545865421639&&
        lng<=126.96774959564209){
            fileName = "인왕산.gpx";
        }
        else if(lat<=37.486471128460956&&
        lng>=127.0514988899231&&
        lat>=37.46993647078212&&
        lng<=127.1021819114685){
            fileName = "대모산.gpx";
        }
        else if(lat<=37.57357943461842&&
        lng>=127.08821296691895&&
        lat>=37.55150993766904&&
        lng<=127.10458517074585){
            fileName = "아차산.gpx";
        }
        else if(lat>=37.56906967170568&&
        lng>=126.91919088363647&&
        lat<=37.5757732700577&&
        lng<=126.93506956100464){
            fileName = "궁동산.gpx";
        }

        if(!fileName.equals("")) {
            try {
                InputStream in = context.getAssets().open(fileName);
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                loadGpxData(xmlPullParser, in);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadGpxData(XmlPullParser parser, InputStream gpxIn) throws XmlPullParserException, IOException {
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
