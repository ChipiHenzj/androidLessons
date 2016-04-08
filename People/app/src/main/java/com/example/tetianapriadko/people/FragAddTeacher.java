package com.example.tetianapriadko.people;


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
import android.widget.Toast;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragAddTeacher extends Fragment {

    private static final String TITLE = "Add Teacher";

    private View rootView;

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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_add_teacher, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            Teacher teacher = new Teacher();
            teacher.setName(((EditText) rootView.findViewById(R.id.edit_name)).getText().toString());
            teacher.setSurname(((EditText) rootView.findViewById(R.id.edit_surname)).getText().toString());
            teacher.setEmail(((EditText) rootView.findViewById(R.id.edit_email)).getText().toString());
            teacher.setPhoneNumber(((EditText) rootView.findViewById(R.id.edit_phone)).getText().toString());
            teacher.setSpeciality(((EditText) rootView.findViewById(R.id.edit_speciality)).getText().toString());
            teacher.setPlaceofWork(((EditText) rootView.findViewById(R.id.edit_place)).getText().toString());

            teacher.saveAsync(new AsyncCallback<Teacher>() {
                @Override
                public void handleResponse(Teacher response) {
                    Toast.makeText(getActivity(), "Teacher added", Toast.LENGTH_SHORT).show();
//                    getFragmentManager().popBackStack();
                    replaceFragmentBackStack(new FragListTeacher());
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(getActivity(), "Teacher failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
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
