package com.example.tetianapriadko.people;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.backendless.geo.GeoPoint;
import com.example.tetianapriadko.people.application.App;
import com.example.tetianapriadko.people.constants.BACK_SETTINGS;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragEditSelect;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.utils.PermissionUtil;

import java.net.URI;
import java.net.URISyntaxException;

public class FragEditStudent extends Fragment {

    private static final String TITLE = "Edit Student";
    private static final int PICK_IMAGE = 2 ;
    private static final int CROP_IMAGE = 3;
    private static final int SHOW_LOCATION = 4;

    private View rootView;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private EditText speciality;
    private EditText placeOfStudy;
    private FrameLayout layoutProgress;
    private Student selectedStudent;
    private Bitmap selectedBitmap = null;
    private AQuery aQuery;
    private ImageView avatar;
    private ImageView staticMap;
    private GeoPoint geoPoint;
    private String lat;
    private String lng;
    private String url;

    private double latitude;
    private double longitude;

    private static final String DEFAULT_AVATAR_URL = "ic_dish_default.jpg";
    private static final int BITMAP_QUALITY_40 = 40;
    private static final int BITMAP_QUALITY_10 = 10;
    private static final int MAX_BITMAP_SIZE_MB = 10000000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_edit_student, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(TITLE);
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

        initEditText();
        initImageView();

        layoutProgress = ((FrameLayout) rootView.findViewById(R.id.layout_progress));
        layoutProgress.setVisibility(View.GONE);

        if (getArguments() != null) {
            String studentId = getArguments().getString("studentObjectId");
            getStudentFromBE(studentId);
        }

        initStaticMap();
    }

    private void initEditText() {
        name = ((EditText) rootView.findViewById(R.id.edit_name));
        surname = ((EditText) rootView.findViewById(R.id.edit_surname));
        email = ((EditText) rootView.findViewById(R.id.edit_email));
        phone = ((EditText) rootView.findViewById(R.id.edit_phone));
        speciality = ((EditText) rootView.findViewById(R.id.edit_speciality));
        placeOfStudy = ((EditText) rootView.findViewById(R.id.edit_place));
    }

    private void initStaticMap(){
        staticMap = (ImageView)rootView.findViewById(R.id.static_map);
        staticMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivityEditDone.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("place", placeOfStudy.getText().toString());
                startActivityForResult(intent, SHOW_LOCATION);
            }
        });
    }

    private void initImageView() {
        avatar = ((ImageView) rootView.findViewById(R.id.imageView_edit_student));
        avatar.setOnClickListener(avatarClickListener);
    }

    private void getStudentFromBE(String objectID) {
        layoutProgress.setVisibility(View.VISIBLE);
        Backendless.Persistence.of(Student.class).findById(objectID, new AsyncCallback<Student>() {
            @Override
            public void handleResponse(Student response) {
                selectedStudent = response;
                layoutProgress.setVisibility(View.GONE);
                name.setText(response.getName());
                surname.setText(response.getSurname());
                email.setText(response.getEmail());
                phone.setText(response.getPhoneNumber());
                speciality.setText(response.getSpeciality());
                placeOfStudy.setText(response.getPlaceOfStudy());

                geoPoint = response.getGeoPoint();
                setImage(response.getAvatarUrl());

                lat = geoPoint.getLatitude().toString();
                lng = geoPoint.getLongitude().toString();
                url = "http://maps.google.com/maps/api/staticmap?center="
                        + lat + "," + lng
                        + "&zoom=15&size=640x200&sensor=false&markers=color:red%7Clabel%7C"
                        + lat + "," + lng
                        + "&key=AIzaSyBu6hLVBRiORrQlJlCURFDt3aoCQTBTO98";
                setMapStatic(url);

                latitude = response.getGeoPoint().getLatitude();
                longitude = response.getGeoPoint().getLongitude();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
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

    public void setImage(String avatarUrl) {
        aQuery = new AQuery(getActivity());
        aQuery.id(avatar).image(
                String.format("%s%s%s%s",
                        BACK_SETTINGS.SERVER_URL,
                        BACK_SETTINGS.FILES,
                        BACK_SETTINGS.STUDENT_AVATAR_STORE_URL,
                        avatarUrl),
                false,
                false,
                0,
                R.drawable.icon);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_edit_student, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                DlgFragEditSelect dlgFragEditSelect = new DlgFragEditSelect();
                dlgFragEditSelect.setTargetFragment(FragEditStudent.this, 1);
                dlgFragEditSelect.show(getFragmentManager(), dlgFragEditSelect.getDialogTag());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    public int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    private String getLastPartOfUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (URISyntaxException e) {
            return DEFAULT_AVATAR_URL;
        }
    }

    private void uploadStudent(String avatarUrl) {
        if (selectedStudent != null){
            selectedStudent.setName((name.getText().toString()));
            selectedStudent.setSurname((surname.getText().toString()));
            selectedStudent.setEmail((email.getText().toString()));
            selectedStudent.setPhoneNumber((phone.getText().toString()));
            selectedStudent.setSpeciality((speciality.getText().toString()));
            selectedStudent.setPlaceOfStudy((placeOfStudy.getText().toString()));
            selectedStudent.setAvatarUrl(avatarUrl);

            GeoPoint geoPoint = new GeoPoint();
            geoPoint.addMetadata("geopoint", "Place");
            geoPoint.setLatitude(latitude);
            geoPoint.setLongitude(longitude);
            selectedStudent.setGeoPoint(geoPoint);

            selectedStudent.saveAsync(new AsyncCallback<Student>() {
                @Override
                public void handleResponse(Student response) {
                    layoutProgress.setVisibility(View.GONE);
                    Bundle bundle = new Bundle();
                    bundle.putString("studentName", selectedStudent.getName());
                    bundle.putString("studentSurname", selectedStudent.getSurname());
                    bundle.putString("studentId", selectedStudent.getObjectId());
                    FragStudent fragStudent = new FragStudent();
                    fragStudent.setArguments(bundle);
                    replaceFragmentBackStack(fragStudent);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void uploadStudentAvatar(Bitmap bitmap) {
        layoutProgress.setVisibility(View.VISIBLE);
        String avatarUrl = name.getText().toString().replaceAll("\\s+", "")
                + surname.getText().toString().replaceAll("\\s+", "") + ".png";
        int quality = byteSizeOf(bitmap) > MAX_BITMAP_SIZE_MB ? BITMAP_QUALITY_10 : BITMAP_QUALITY_40;
        Backendless.Files.Android.upload(bitmap,
                Bitmap.CompressFormat.PNG,
                quality,
                avatarUrl,
                BACK_SETTINGS.STUDENT_AVATAR_STORE_URL,
                true,
                studentAvatarCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        if(selectedBitmap != null){
                            uploadStudentAvatar(selectedBitmap);
                        } else {
                            uploadStudent(selectedStudent.getAvatarUrl());
                        }

                    break;
                    case PICK_IMAGE:
                        Uri selectedImage = data.getData();
                        Intent intent = new Intent(getActivity(), ActCrop.class);
                        intent.putExtra("Path", selectedImage);
                        startActivityForResult(intent, CROP_IMAGE);
                        break;
                    case CROP_IMAGE:
                        selectedBitmap = App.getCroppedBitmap();
                        avatar.setImageBitmap(selectedBitmap);
                        break;
                    case SHOW_LOCATION:
                        double latitude = data.getDoubleExtra("latitude", -1);
                        double longitude = data.getDoubleExtra("longitude", -1);
                        this.latitude = latitude;
                        this.longitude = longitude;
                        url = "http://maps.google.com/maps/api/staticmap?center="
                                + this.latitude  + "," + this.longitude
                                + "&zoom=15&size=640x200&sensor=false&markers=color:red%7Clabel%7C"
                                + this.latitude  + "," + this.longitude
                                + "&key=AIzaSyBu6hLVBRiORrQlJlCURFDt3aoCQTBTO98";
                        setMapStatic(url);
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

    protected void replaceFragmentBackStack(android.support.v4.app.Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frag_container, fragment)
                .addToBackStack("frag")
                .commit();
    }

    private View.OnClickListener avatarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            procStoragePermiss();
        }
    };

    private void procStoragePermiss() {
        if (!PermissionUtil.checkPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                || !PermissionUtil.checkPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(1, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
            pickImage();
        } else {
            pickImage();
        }
    }

    private void requestPermission(int requestCode, String[] permission) {
        ActivityCompat.requestPermissions(getActivity(), permission, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionUtil.verifyPermissions(grantResults)) {
            pickImage();
        } else {
            requestPermission(1, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }

    private AsyncCallback<BackendlessFile> studentAvatarCallback = new AsyncCallback<BackendlessFile>() {
        @Override
        public void handleResponse(BackendlessFile response) {
            avatar.setImageBitmap(App.getCroppedBitmap());
            uploadStudent(getLastPartOfUrl(response.getFileURL()));
        }

        @Override
        public void handleFault(BackendlessFault fault) {
            layoutProgress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
