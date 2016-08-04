package com.example.tetianapriadko.people;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.example.tetianapriadko.people.dialog_fragments.DlgFragAddStudentDone;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.utils.PermissionUtil;

import java.net.URI;
import java.net.URISyntaxException;

public class FragAddStudent extends Fragment {

    private static final String TITLE = "Add Student";
    private static final int PICK_IMAGE = 2;
    private static final int CROP_IMAGE = 3;
    private static final int SET_LOCATION = 4;

    private View rootView;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private EditText speciality;
    private EditText place;
    private TextView setLocation;
    private FrameLayout layoutProgress;
    private ImageView avatar;
    private Bitmap selectedBitmap;
    private ImageView staticMap;
    private String url;
    private AQuery aQuery;

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
        rootView = inflater.inflate(R.layout.frag_add_student, container, false);
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

        layoutProgress = (FrameLayout) rootView.findViewById(R.id.layout_progress);
        layoutProgress.setVisibility(View.GONE);

        initEditText();
        initTextView();
        initImageView();
    }

    private void initEditText() {
        name = ((EditText) rootView.findViewById(R.id.edit_name));
        surname = ((EditText) rootView.findViewById(R.id.edit_surname));
        email = ((EditText) rootView.findViewById(R.id.edit_email));
        phone = ((EditText) rootView.findViewById(R.id.edit_phone));
        speciality = ((EditText) rootView.findViewById(R.id.edit_speciality));
        place = ((EditText) rootView.findViewById(R.id.edit_place));
    }

    private void initImageView() {
        avatar = ((ImageView) rootView.findViewById(R.id.imageView_add_student));
        avatar.setOnClickListener(avatarClickListener);
    }

    private void initTextView() {
        setLocation = (TextView) rootView.findViewById(R.id.set_location);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), MapsActivityAddDone.class), SET_LOCATION);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_add_student, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (TextUtils.isEmpty(name.getText().toString()) ||
                        TextUtils.isEmpty(surname.getText().toString()) ||
                        TextUtils.isEmpty(email.getText().toString()) ||
                        TextUtils.isEmpty(phone.getText().toString()) ||
                        TextUtils.isEmpty(speciality.getText().toString()) ||
                        TextUtils.isEmpty(place.getText().toString())) {
                    name.setError("Empty name");
                    surname.setError("Empty name");
                    email.setError("Empty name");
                    phone.setError("Empty name");
                    speciality.setError("Empty name");
                    place.setError("Empty name");
                } else {
                    name.setError(null);
                    surname.setError(null);
                    email.setError(null);
                    phone.setError(null);
                    speciality.setError(null);
                    place.setError(null);

                    DlgFragAddStudentDone studentDone = new DlgFragAddStudentDone();
                    studentDone.setTargetFragment(FragAddStudent.this, 1);
                    studentDone.show(getFragmentManager(), studentDone.getDialogTag());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Student student = new Student();
        student.setName((name.getText().toString()));
        student.setSurname((surname.getText().toString()));
        student.setEmail((email.getText().toString()));
        student.setPhoneNumber((phone.getText().toString()));
        student.setSpeciality((speciality.getText().toString()));
        student.setPlaceOfStudy((place.getText().toString()));
        student.setAvatarUrl(getLastPartOfUrl(avatarUrl));

        GeoPoint geoPoint = new GeoPoint();
        geoPoint.setLatitude(latitude);
        geoPoint.setLongitude(longitude);
        student.setGeoPoint(geoPoint);

        student.saveAsync(new AsyncCallback<Student>() {
            @Override
            public void handleResponse(Student response) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Student added", Toast.LENGTH_SHORT).show();
                //                    getFragmentManager().popBackStack();
                replaceFragmentBackStack(new FragListStudent());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                studentAvatarCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        uploadStudentAvatar(selectedBitmap);
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
                    case SET_LOCATION:
                        double latitude = data.getDoubleExtra("latitude", -1);
                        double longitude = data.getDoubleExtra("longitude", -1);
                        this.latitude = latitude;
                        this.longitude = longitude;
                        setLocation.setText("");
                        staticMap = (ImageView)rootView.findViewById(R.id.static_map);
                        url = "http://maps.google.com/maps/api/staticmap?center="
                                + this.latitude + "," + this.longitude
                                + "&zoom=15&size=640x200&sensor=false&markers=color:red%7Clabel%7C"
                                + this.latitude + "," + this.longitude
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

    protected void replaceFragmentBackStack(Fragment fragment) {
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
            uploadStudent(getLastPartOfUrl(response.getFileURL()));
        }

        @Override
        public void handleFault(BackendlessFault fault) {
            layoutProgress.setVisibility(View.GONE);
            Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}