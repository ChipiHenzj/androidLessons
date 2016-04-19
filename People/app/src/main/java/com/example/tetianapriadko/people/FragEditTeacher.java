package com.example.tetianapriadko.people;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragAddTeacherDone;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragEditSelect;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragEditTeacher extends Fragment {

    private static final String TITLE = "Edit Teacher";

    private View rootView;
    private FrameLayout layoutProgress;
    private Teacher selectedTeacher;

    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private EditText speciality;
    private EditText place;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_edit_teacher, container, false);
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

        layoutProgress = (FrameLayout)rootView.findViewById(R.id.layout_progress);
        layoutProgress.setVisibility(View.GONE);

        initEditText();

        if (getArguments() != null){
            String teacherId = getArguments().getString("teacherObjectId");
            getTeacherFromBE(teacherId);
        }

    }

    private void initEditText() {
        name = ((EditText) rootView.findViewById(R.id.edit_name));
        surname = ((EditText) rootView.findViewById(R.id.edit_surname));
        email = ((EditText) rootView.findViewById(R.id.edit_email));
        phone = ((EditText) rootView.findViewById(R.id.edit_phone));
        speciality = ((EditText) rootView.findViewById(R.id.edit_speciality));
        place = ((EditText) rootView.findViewById(R.id.edit_place));
    }

    private void getTeacherFromBE(String objectID) {
        layoutProgress.setVisibility(View.GONE);
        Backendless.Persistence.of(Teacher.class).findById(objectID, new AsyncCallback<Teacher>() {
            @Override
            public void handleResponse(Teacher response) {
                selectedTeacher = response;
                name.setText(response.getName());
                surname.setText(response.getSurname());
                email.setText(response.getEmail());
                phone.setText(response.getPhoneNumber());
                speciality.setText(response.getSpeciality());
                place.setText(response.getPlaceofWork());
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
        inflater.inflate(R.menu.frag_edit_teacher, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                DlgFragEditSelect dlgFragEditSelect = new DlgFragEditSelect();
                dlgFragEditSelect.setTargetFragment(FragEditTeacher.this, 1);
                dlgFragEditSelect.show(getFragmentManager(), dlgFragEditSelect.getDialogTag());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        if (selectedTeacher != null){
                            selectedTeacher.setName((name.getText().toString()));
                            selectedTeacher.setSurname((surname.getText().toString()));
                            selectedTeacher.setEmail((email.getText().toString()));
                            selectedTeacher.setPhoneNumber((phone.getText().toString()));
                            selectedTeacher.setSpeciality((speciality.getText().toString()));
                            selectedTeacher.setPlaceofWork((place.getText().toString()));

                            selectedTeacher.saveAsync(new AsyncCallback<Teacher>() {
                                @Override
                                public void handleResponse(Teacher response) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("teacherName", selectedTeacher.getName());
                                    bundle.putString("teacherSurname", selectedTeacher.getSurname());
                                    bundle.putString("teacherId", selectedTeacher.getObjectId());
                                    FragTeacher fragTeacher = new FragTeacher();
                                    fragTeacher.setArguments(bundle);
                                    replaceFragmentBackStack(fragTeacher);
                                }
                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(getActivity(), "Teacher failed", Toast.LENGTH_SHORT).show();
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

    protected void replaceFragmentBackStack(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frag_container, fragment)
                .addToBackStack("frag")
                .commit();
    }

}
