package com.example.tetianapriadko.people;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragEditSelect;
import com.example.tetianapriadko.people.structure.Student;

public class FragEditStudent extends Fragment {
    private static final String TITLE = "Edit Student";

    private View rootView;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private EditText speciality;
    private EditText placeOfStudy;
    private FrameLayout layoutProgress;
    private Student selectedStudent;

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
        layoutProgress = ((FrameLayout) rootView.findViewById(R.id.layout_progress));
        layoutProgress.setVisibility(View.GONE);

        if (getArguments() != null) {
            String studentId = getArguments().getString("studentObjectId");
            getStudentFromBE(studentId);
        }
    }

    private void initEditText() {
        name = ((EditText) rootView.findViewById(R.id.edit_name));
        surname = ((EditText) rootView.findViewById(R.id.edit_surname));
        email = ((EditText) rootView.findViewById(R.id.edit_email));
        phone = ((EditText) rootView.findViewById(R.id.edit_phone));
        speciality = ((EditText) rootView.findViewById(R.id.edit_speciality));
        placeOfStudy = ((EditText) rootView.findViewById(R.id.edit_place));
    }


    private void getStudentFromBE(String objectID) {
        layoutProgress.setVisibility(View.VISIBLE);
        Backendless.Persistence.of(Student.class).findById(objectID, new AsyncCallback<Student>() {
            @Override
            public void handleResponse(Student response) {
                selectedStudent = response;
                layoutProgress.setVisibility(View.GONE);
                ((EditText) rootView.findViewById(R.id.edit_name))
                        .setText(response.getName());
                ((EditText) rootView.findViewById(R.id.edit_surname))
                        .setText(response.getSurname());
                ((EditText) rootView.findViewById(R.id.edit_email))
                        .setText(response.getEmail());
                ((EditText) rootView.findViewById(R.id.edit_phone))
                        .setText(response.getPhoneNumber());
                ((EditText) rootView.findViewById(R.id.edit_speciality))
                        .setText(response.getSpeciality());
                ((EditText) rootView.findViewById(R.id.edit_place))
                        .setText(response.getPlaceOfStudy());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
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

    protected void replaceFragmentBackStack(android.support.v4.app.Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frag_container, fragment)
                .addToBackStack("frag")
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        layoutProgress.setVisibility(View.VISIBLE);
                        if (selectedStudent != null){
                            selectedStudent.setName((name.getText().toString()));
                            selectedStudent.setSurname((surname.getText().toString()));
                            selectedStudent.setEmail((email.getText().toString()));
                            selectedStudent.setPhoneNumber((phone.getText().toString()));
                            selectedStudent.setSpeciality((speciality.getText().toString()));
                            selectedStudent.setPlaceOfStudy((placeOfStudy.getText().toString()));
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
                                    Toast.makeText(getActivity(), "Student failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

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
