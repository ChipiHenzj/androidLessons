package com.example.tetianapriadko.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class Lesson140 extends AppCompatActivity {

    SupportMapFragment mapFragment;
    GoogleMap map;
    Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson140);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null) {
            finish();
            return;
        }

        init();

    }

    private void init() {

        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(15, -30)).add(new LatLng(15, -20))
                .add(new LatLng(20, -20)).add(new LatLng(20, -30))
                .color(Color.MAGENTA)
                .width(5);

        map.addPolyline(polylineOptions);


        PolygonOptions polygoneOptions = new PolygonOptions()
                .add(new LatLng(15, -10)).add(new LatLng(15, 0))
                .add(new LatLng(20, 0)).add(new LatLng(20, -10))
                .strokeColor(Color.YELLOW)
                .strokeWidth(10)
                .fillColor(Color.BLACK);

        map.addPolygon(polygoneOptions);

        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(18, 15)).radius(500000)
                .fillColor(Color.YELLOW)
                .strokeColor(Color.DKGRAY)
                .strokeWidth(10);

        map.addCircle(circleOptions);





        List<LatLng> list = new ArrayList<LatLng>();
        list.add(new LatLng(-4, -5));
        list.add(new LatLng(0, -1));
        list.add(new LatLng(4, -5));
        list.add(new LatLng(0, -9));

        PolygonOptions polygoneOptions1 = new PolygonOptions()
                .add(new LatLng(-5, -10))
                .add(new LatLng(-5, 0))
                .add(new LatLng(5, 0))
                .add(new LatLng(5, -10))
                .addHole(list)
                .strokeColor(Color.CYAN).
                        strokeWidth(5).
                        fillColor(Color.YELLOW);

        map.addPolygon(polygoneOptions1);




        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(new LatLng(50, 50), 500000f, 500000f)
                .transparency((float) 0.5);
        map.addGroundOverlay(newarkMap);

    }

    public void onClickTest(View view) {

        map.addMarker(new MarkerOptions()
                .position(new LatLng(56.9471, 24.1158))
                .title("Hello Riga")
                .snippet("Additional text")
                .draggable(true));





        map.addMarker(new MarkerOptions().position(new LatLng(-30, -10)).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        map.addMarker(new MarkerOptions().position(new LatLng(-30, 0)).icon(
                BitmapDescriptorFactory.defaultMarker()));

        map.addMarker(new MarkerOptions().position(new LatLng(-30, 10)).icon(
                BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

    }
}
