package com.example.tetianapriadko.people;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteStudList;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteStudent;
import com.example.tetianapriadko.people.structure.Student;

public class FragStudent extends Fragment {

    private View rootView;
    private String selectedStudentId;
    private Student selectedStudent;
    private FrameLayout layoutProgress;

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

        layoutProgress = (FrameLayout)rootView.findViewById(R.id.layout_progress);
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
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
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
