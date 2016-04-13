package com.example.tetianapriadko.people;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteTeacher;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragTeacher extends Fragment {

    private View rootView;

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
        String one = bundle.getString("teacherName");
        String two = bundle.getString("teacherSurname");
        toolbar.setTitle(one + " " + two);

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


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }
        });


        getTeacherFromBE(bundle.getString("teacherId"));
    }

    private void getTeacherFromBE(String objectID) {
        Backendless.Persistence.of(Teacher.class).findById(objectID, new AsyncCallback<Teacher>() {
            @Override
            public void handleResponse(Teacher response) {
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_name))
                        .setText(response.getName());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_surname))
                        .setText(response.getName());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_phone))
                        .setText(response.getName());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_email))
                        .setText(response.getName());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_speciality))
                        .setText(response.getName());
                ((TextView) rootView.findViewById(R.id.fromBE_teacher_place))
                        .setText(response.getName());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
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
        inflater.inflate(R.menu.frag_teacher, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            DlgFragDeleteTeacher teacherDelete = new DlgFragDeleteTeacher();
            teacherDelete.setTargetFragment(FragTeacher.this, 1);
            teacherDelete.show(getFragmentManager(), teacherDelete.getDialogTag());
        }
        return super.onOptionsItemSelected(item);
    }

}
