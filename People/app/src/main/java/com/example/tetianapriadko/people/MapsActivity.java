package com.example.tetianapriadko.people;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private FrameLayout layoutProgress;

    private List<Student> students;
    private List<Teacher> teachers;

    private ArrayList<GeoPoint> geoPointsStudents;
    private ArrayList<GeoPoint> geoPointsTeachers;
    private ArrayList<String> placesOfStudents;
    private ArrayList<String> placesOfTeachers;
    private ArrayList<String> namesOfStudents;
    private ArrayList<String> namesOfTeachers;
    private ArrayList<String> surnamesOfStudents;
    private ArrayList<String> surnamesOfTeachers;

    private LatLng markersShow;
    private double latitudeStudent;
    private double longitudeStudent;
    private double latitudeTeacher;
    private double longitudeTeacher;

    private String placeStudent;
    private String nameStudent;
    private String surnameStudent;
    private String placeTeacher;
    private String nameTeacher;
    private String surnameTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
            getStudentList();
            getTeacherList();
        } else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
    }

    private void getStudentList() {
        layoutProgress.setVisibility(View.VISIBLE);
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Student.class).find(query, new AsyncCallback<BackendlessCollection<Student>>() {
            @Override
            public void handleResponse(BackendlessCollection<Student> response) {
                layoutProgress.setVisibility(View.GONE);
                students = response.getCurrentPage();

                geoPointsStudents = new ArrayList<GeoPoint>();
                placesOfStudents = new ArrayList<String>();
                namesOfStudents = new ArrayList<String>();
                surnamesOfStudents = new ArrayList<String>();

                for (int i = 0; i < students.size(); ++i) {
                    geoPointsStudents.add(students.get(i).getGeoPoint());
                    placesOfStudents.add(students.get(i).getPlaceOfStudy());
                    namesOfStudents.add(students.get(i).getName());
                    surnamesOfStudents.add(students.get(i).getSurname());
                }

                for (int i = 0; i < geoPointsStudents.size(); ++i) {
                    if (geoPointsStudents.get(i) != null) {
                        latitudeStudent = geoPointsStudents.get(i).getLatitude();
                        longitudeStudent = geoPointsStudents.get(i).getLongitude();
                        markersShow = new LatLng(latitudeStudent, longitudeStudent);

                        placeStudent = placesOfStudents.get(i);
                        nameStudent = namesOfStudents.get(i);
                        surnameStudent = surnamesOfStudents.get(i);

//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(show, 10.f));
                        mMap.addMarker(new MarkerOptions()
                            .position(markersShow)
                            .title(placeStudent)
                            .snippet(nameStudent + " " + surnameStudent))
                            .showInfoWindow();
                    }
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(MapsActivity.this, fault.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTeacherList() {
        layoutProgress.setVisibility(View.VISIBLE);
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Teacher.class).find(query, new AsyncCallback<BackendlessCollection<Teacher>>() {
            @Override
            public void handleResponse(BackendlessCollection<Teacher> response) {
                layoutProgress.setVisibility(View.GONE);
                teachers = response.getCurrentPage();

                geoPointsTeachers = new ArrayList<GeoPoint>();
                placesOfTeachers = new ArrayList<String>();
                namesOfTeachers = new ArrayList<String>();
                surnamesOfTeachers = new ArrayList<String>();

                for (int i = 0; i < teachers.size(); ++i) {
                    geoPointsTeachers.add(teachers.get(i).getGeoPoint());
                    placesOfTeachers.add(teachers.get(i).getPlaceofWork());
                    namesOfTeachers.add(teachers.get(i).getName());
                    surnamesOfTeachers.add(teachers.get(i).getSurname());
                }

                for (int i = 0; i < geoPointsTeachers.size(); ++i) {
                    if (geoPointsTeachers.get(i) != null) {
                        latitudeTeacher = geoPointsTeachers.get(i).getLatitude();
                        longitudeTeacher = geoPointsTeachers.get(i).getLongitude();
                        markersShow = new LatLng(latitudeTeacher, longitudeTeacher);

                        placeTeacher = placesOfTeachers.get(i);
                        nameTeacher = namesOfTeachers.get(i);
                        surnameTeacher = surnamesOfTeachers.get(i);

                        mMap.addMarker(new MarkerOptions()
                                .position(markersShow)
                                .title(placeTeacher)
                                .snippet(nameTeacher + " " + surnameTeacher))
                                .showInfoWindow();
                    }
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(MapsActivity.this, fault.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        } else {

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
