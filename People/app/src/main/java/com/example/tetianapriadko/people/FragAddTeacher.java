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

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragAddStudentDone;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragAddTeacherDone;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragAddTeacher extends Fragment {

    private static final String TITLE = "Add Teacher";

    private View rootView;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private EditText speciality;
    private EditText place;
    private FrameLayout layoutProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_add_teacher, container, false);
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
    }

    private void initEditText() {
        name = ((EditText) rootView.findViewById(R.id.edit_name));
        surname = ((EditText) rootView.findViewById(R.id.edit_surname));
        email = ((EditText) rootView.findViewById(R.id.edit_email));
        phone = ((EditText) rootView.findViewById(R.id.edit_phone));
        speciality = ((EditText) rootView.findViewById(R.id.edit_speciality));
        place = ((EditText) rootView.findViewById(R.id.edit_place));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_add_teacher, menu);
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

                    DlgFragAddTeacherDone teacherDone = new DlgFragAddTeacherDone();
                    teacherDone.setTargetFragment(FragAddTeacher.this, 1);
                    teacherDone.show(getFragmentManager(), teacherDone.getDialogTag());
                }
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
                        layoutProgress.setVisibility(View.VISIBLE);
                        Teacher teacher = new Teacher();
                        teacher.setName((name.getText().toString()));
                        teacher.setSurname((surname.getText().toString()));
                        teacher.setEmail((email.getText().toString()));
                        teacher.setPhoneNumber((phone.getText().toString()));
                        teacher.setSpeciality((speciality.getText().toString()));
                        teacher.setPlaceofWork((place.getText().toString()));

                        teacher.saveAsync(new AsyncCallback<Teacher>() {
                            @Override
                            public void handleResponse(Teacher response) {
                                layoutProgress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Teacher added", Toast.LENGTH_SHORT).show();
                                //                    getFragmentManager().popBackStack();
                                replaceFragmentBackStack(new FragListTeacher());
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                layoutProgress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Teacher failed", Toast.LENGTH_SHORT).show();
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

    protected void replaceFragmentBackStack(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frag_container, fragment)
                .addToBackStack("frag")
                .commit();
    }


}