package com.example.tetianapriadko.people;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragLocationEditDone;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapsActivityEditDone extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient client;

    private LatLng show;
    private double latitude;
    private double longitude;
    private FrameLayout layoutProgress;
    private Marker marker = null;
    private String place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity_edit_done);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        layoutProgress = (FrameLayout) findViewById(R.id.layout_progress);
        layoutProgress.setVisibility(View.GONE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getGeoPoint();
        } else {
            ActivityCompat.requestPermissions(MapsActivityEditDone.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                show = latLng;
                if (marker == null) {
                    marker = mMap.addMarker(new MarkerOptions()
                            .position(show)
                            .draggable(true));
                } else {
                    marker.setPosition(show);
                }
            }
        });
    }

    public void getGeoPoint() {
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", -2);
        longitude = intent.getDoubleExtra("longitude", -2);
        place = intent.getStringExtra("place");
        show = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(show, 16.0f));

        marker = mMap.addMarker(new MarkerOptions()
                .position(show)
                .title(place)
                .draggable(true));
        marker.showInfoWindow();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            getGeoPoint();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (show == null) {
                    Toast.makeText(this, "Please long press for set up Location", Toast.LENGTH_SHORT).show();
                } else {
                    DlgFragLocationEditDone dlgFragLocationEditDone = new DlgFragLocationEditDone();
                    dlgFragLocationEditDone.setTargetActivity(MapsActivityEditDone.this, 1);
                    dlgFragLocationEditDone.show(getSupportFragmentManager(), "");
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void customActivityResult(int requestCode, int resultCode) {
        if (resultCode == RESULT_OK) {
            layoutProgress.setVisibility(View.VISIBLE);
            Map<String, Object> meta = new HashMap<>();
            meta.put("geopoint", "Place");

            Backendless.Geo.savePoint(show.latitude, show.longitude, meta, new AsyncCallback<GeoPoint>() {
                @Override
                public void handleResponse(GeoPoint geoPoint) {
                    layoutProgress.setVisibility(View.GONE);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("latitude", show.latitude);
                    returnIntent.putExtra("longitude", show.longitude);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(MapsActivityEditDone.this, backendlessFault.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        } else {
            mMap.clear();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.tetianapriadko.people/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Maps Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.tetianapriadko.people/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
