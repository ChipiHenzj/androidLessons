package com.example.tetianapriadko.people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.tetianapriadko.people.adapter.AdapterStudents;
import com.example.tetianapriadko.people.adapter.AdapterTeachers;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragListTeacher extends Fragment{

    private static final String TITLE = "List of Teachers";

    private View rootView;
    private RecyclerView recViewTeacherList;
    AdapterTeachers adapterTeachers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.frag_list_teacher, container, false);
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

        initRecyclerView();

        getTeacherList();

    }

    private void initRecyclerView(){
        LinearLayoutManager studentLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        adapterTeachers = new AdapterTeachers(null);
        adapterTeachers.setItemClickListener(teacherListener);
        recViewTeacherList = ((RecyclerView) rootView.findViewById(R.id.recView_teacher_list));
        recViewTeacherList.setLayoutManager(studentLayoutManager);
        recViewTeacherList.setHasFixedSize(true);
        recViewTeacherList.setAdapter(adapterTeachers);
    }

    private void getTeacherList() {
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Teacher.class).find(query,
                new AsyncCallback<BackendlessCollection<Teacher>>() {
            @Override
            public void handleResponse(BackendlessCollection<Teacher> response) {
                Toast.makeText(getActivity(), "Loaded " + response.getCurrentPage().size(),
                        Toast.LENGTH_SHORT).show();
                adapterTeachers.setData(response.getCurrentPage());
                adapterTeachers.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_list_teacher, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            replaceFragmentBackStack(new FragAddTeacher());
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

    private AdapterTeachers.OnItemClickListener teacherListener = new AdapterTeachers.OnItemClickListener() {
        @Override
        public void itemClicked(View view, int position, Teacher teacher) {
            Bundle bundle = new Bundle();
            bundle.putString("teacherName", teacher.getName());
            bundle.putString("teacherSurname", teacher.getSurname());
            FragTeacher fragTeacher = new FragTeacher();
            fragTeacher.setArguments(bundle);
            replaceFragmentBackStack(fragTeacher);

            Toast.makeText(getActivity(), "Clicked " + position, Toast.LENGTH_SHORT).show();
        }
    };
}
