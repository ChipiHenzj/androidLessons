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
import com.example.tetianapriadko.people.constants.BACK_SETTINGS;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteStudent;
import com.example.tetianapriadko.people.structure.Student;

public class FragStudent extends Fragment {

    private View rootView;
    private String selectedStudentId;
    private Student selectedStudent;
    private FrameLayout layoutProgress;
    private ImageView avatar;
    private AQuery aQuery;
    private TextView fromBE_student_phone;
    private TextView fromBE_student_email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_student, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        Bundle bundle = this.getArguments();
        String name = bundle.getString("studentName");
        String surname = bundle.getString("studentSurname");
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

        fromBE_student_phone = ((TextView) rootView.findViewById(R.id.fromBE_student_phone));
        callPhone();
        fromBE_student_email = ((TextView) rootView.findViewById(R.id.fromBE_student_email));
        sendEmail();


        layoutProgress = (FrameLayout) rootView.findViewById(R.id.layout_progress);
        layoutProgress.setVisibility(View.GONE);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle studentIdBundle = new Bundle();
                studentIdBundle.putString("studentObjectId", selectedStudentId);
                FragEditStudent editStudent = new FragEditStudent();
                editStudent.setArguments(studentIdBundle);
                replaceFragmentBackStack(editStudent);
            }
        });

        getStudentFromBE(bundle.getString("studentId"));
    }

    private void callPhone (){
        fromBE_student_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + fromBE_student_phone.getText().toString()));
                startActivity(callIntent);
            }
        });
    }

    private void sendEmail(){
        fromBE_student_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("mailto:" + fromBE_student_email.getText().toString()));
                startActivity(sendIntent);
            }
        });
    }

    private void getStudentFromBE(String objectID) {
        layoutProgress.setVisibility(View.VISIBLE);
        Backendless.Persistence.of(Student.class).findById(objectID, new AsyncCallback<Student>() {
            @Override
            public void handleResponse(Student response) {
                selectedStudentId = response.getObjectId();
                selectedStudent = response;
                layoutProgress.setVisibility(View.GONE);
                ((TextView) rootView.findViewById(R.id.fromBE_student_name))
                        .setText(response.getName());
                ((TextView) rootView.findViewById(R.id.fromBE_student_surname))
                        .setText(response.getSurname());
                ((TextView) rootView.findViewById(R.id.fromBE_student_email))
                        .setText(response.getEmail());
                ((TextView) rootView.findViewById(R.id.fromBE_student_phone))
                        .setText(response.getPhoneNumber());
                ((TextView) rootView.findViewById(R.id.fromBE_student_speciality))
                        .setText(response.getSpeciality());
                ((TextView) rootView.findViewById(R.id.fromBE_student_place))
                        .setText(response.getPlaceOfStudy());
                avatar = ((ImageView) rootView.findViewById(R.id.imageView_avatar_student));
                setImage(response.getAvatarUrl());
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), fault.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
        inflater.inflate(R.menu.frag_student, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            DlgFragDeleteStudent studentDelete = new DlgFragDeleteStudent();
            studentDelete.setTargetFragment(FragStudent.this, 1);
            studentDelete.show(getFragmentManager(), studentDelete.getDialogTag());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        layoutProgress.setVisibility(View.VISIBLE);
                        selectedStudent.removeAsync(new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                layoutProgress.setVisibility(View.GONE);
                                replaceFragmentBackStack(new FragListStudent());
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                layoutProgress.setVisibility(View.GONE);
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
