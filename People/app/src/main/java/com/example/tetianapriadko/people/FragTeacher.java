package com.example.tetianapriadko.people;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.example.tetianapriadko.people.constants.BACK_SETTINGS;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteTeacher;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragTeacher extends Fragment {

    private View rootView;
    private String selectedTeacherId;
    private Teacher selectedTeacher;
    private FrameLayout layoutProgress;
    private ImageView avatar;
    private AQuery aQuery;
    private TextView fromBE_teacher_phone;
    private TextView fromBE_teacher_email;
    private TextView fromBE_teacher_place;
    private GeoPoint geoPoint;
    private String lat;
    private String lng;
    private String url;
    private ImageView staticMap;
    private double from_BE_latitude;
    private double from_BE_longitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_teacher, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        Bundle bundle = this.getArguments();
        String name = bundle.getString("teacherName");
        String surname = bundle.getString("teacherSurname");
        toolbar.setTitle(name + " " + surname);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new
                android.support.v7.app.ActionBarDrawerToggle(
                getActivity(),
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fromBE_teacher_phone = ((TextView) rootView.findViewById(R.id.fromBE_teacher_phone));
        callPhone();

        fromBE_teacher_email = ((TextView) rootView.findViewById(R.id.fromBE_teacher_email));
        sendEmail();

        staticMap = (ImageView)rootView.findViewById(R.id.static_map);
        fromBE_teacher_place = (TextView)rootView.findViewById(R.id.fromBE_teacher_place);
        staticMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivityShow.class);
                intent.putExtra("latitude", from_BE_latitude);
                intent.putExtra("longitude", from_BE_longitude);
                intent.putExtra("place", fromBE_teacher_place.getText().toString());
                startActivity(intent);
            }
        });

        layoutProgress = (FrameLayout)rootView.findViewById(R.id.layout_progress);
        layoutProgress.setVisibility(View.GONE);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle teacherIdBundle = new Bundle();
                teacherIdBundle.putString("teacherObjectId", selectedTeacherId);
                FragEditTeacher editTeacher = new FragEditTeacher();
                editTeacher.setArguments(teacherIdBundle);
                replaceFragmentBackStack(editTeacher);
            }
        });

        getTeacherFromBE(bundle.getString("teacherId"));
    }

    private void callPhone(){
        fromBE_teacher_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + fromBE_teacher_phone.getText().toString()));
                startActivity(callIntent);
            }
        });
    }

    private void sendEmail(){
        fromBE_teacher_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:" + fromBE_teacher_email.getText().toString()));
                startActivity(sendIntent);
            }
        });
    }

    private void getTeacherFromBE(String objectID) {
        layoutProgress.setVisibility(View.VISIBLE);
        Backendless.Persistence.of(Teacher.class).findById(objectID, new AsyncCallback<Teacher>() {
            @Override
            public void handleResponse(Teacher response) {
                selectedTeacherId = response.getObjectId();
                selectedTeacher = response;
                layoutProgress.setVisibility(View.GONE);
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_name))
                        .setText(response.getName());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_surname))
                        .setText(response.getSurname());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_email))
                        .setText(response.getEmail());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_phone))
                        .setText(response.getPhoneNumber());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_speciality))
                        .setText(response.getSpeciality());
                fromBE_teacher_place.setText(response.getPlaceofWork());
                avatar = ((ImageView) rootView.findViewById(R.id.imageView_avatar_teacher));
                setImage(response.getAvatarUrl());

                geoPoint = response.getGeoPoint();
                lat = geoPoint.getLatitude().toString();
                lng = geoPoint.getLongitude().toString();
                url = "http://maps.google.com/maps/api/staticmap?center="
                        + lat + "," + lng
                        + "&zoom=15&size=640x200&sensor=false&markers=color:red%7Clabel%7C"
                        + lat + "," + lng
                        + "&key=AIzaSyBu6hLVBRiORrQlJlCURFDt3aoCQTBTO98";
                setMapStatic(url);

                from_BE_latitude = geoPoint.getLatitude();
                from_BE_longitude = geoPoint.getLongitude();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMapStatic(String url) {
        aQuery = new AQuery(getActivity());
        aQuery.id(staticMap).image(
                url,
                false,
                false,
                0,
                R.drawable.icon);
    }

    public void setImage (String avatarUrl){
        aQuery = new AQuery(getActivity());
        aQuery.id(avatar).image(
                String.format("%s%s%s%s",
                        BACK_SETTINGS.SERVER_URL,
                        BACK_SETTINGS.FILES,
                        BACK_SETTINGS.TEACHER_AVATAR_STORE_URL,
                        avatarUrl),
                false,
                false,
                0,
                R.drawable.icon);

    }

    protected void replaceFragmentBackStack(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frag_container, fragment)
                .addToBackStack("frag")
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_teacher, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                DlgFragDeleteTeacher teacherDelete = new DlgFragDeleteTeacher();
                teacherDelete.setTargetFragment(FragTeacher.this, 1);
                teacherDelete.show(getFragmentManager(), teacherDelete.getDialogTag());
                break;
            case R.id.action_share:
                shareTeacher();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareTeacher() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT,
                String.format("%s%s%s%s",
                        "Hi. I'd like to show you Teacher: ",
                        selectedTeacher.getName(),
                        " ",
                        selectedTeacher.getSurname()));
        intent.putExtra(Intent.EXTRA_TEXT,
                String.format("%s%s%s%s",
                        BACK_SETTINGS.SERVER_URL,
                        BACK_SETTINGS.FILES,
                        BACK_SETTINGS.TEACHER_AVATAR_STORE_URL,
                        selectedTeacher.getAvatarUrl()));
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        layoutProgress.setVisibility(View.VISIBLE);
                        selectedTeacher.removeAsync(new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                layoutProgress.setVisibility(View.GONE);
                                replaceFragmentBackStack(new FragListTeacher());
                            }
                            @Override
                            public void handleFault(BackendlessFault fault) {
                                layoutProgress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
                break;
            case Activity.RESULT_CANCELED:
                switch (requestCode) {
                    case 1:
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
