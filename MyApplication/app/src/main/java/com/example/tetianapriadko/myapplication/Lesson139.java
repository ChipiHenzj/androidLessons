package com.example.tetianapriadko.myapplication;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Lesson139 extends FragmentActivity{

    SupportMapFragment mapFragment;
    GoogleMap map;

    final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson139);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        map = mapFragment.getMap();

        if (map == null) {
            finish();
            return;
        }
        init();
    }

    private void init() {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "onMapClick: " + latLng.latitude + "," + latLng.longitude);
            }
        });

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d(TAG, "onMapLongClick: " + latLng.latitude + "," + latLng.longitude);
            }
        });

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition camera) {
                Log.d(TAG, "onCameraChange: " + camera.target.latitude + "," + camera.target.longitude);
            }
        });

    }


    public void onClickTest(View view) {
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);



        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(56.9471,24.1158))
                .zoom(10)
                .bearing(3)
                .tilt(0)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);

//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(
//                56.9471,24.1158));
//        map.animateCamera(cameraUpdate);


//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
//                new LatLngBounds(new LatLng( 56.9471,24.1158), new LatLng( 56.9471,24.1158)),
//                100);
//        map.animateCamera(cameraUpdate);

    }
}
