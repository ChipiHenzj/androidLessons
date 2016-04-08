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
import com.example.tetianapriadko.people.structure.Student;

public class FragListStudent extends Fragment {

    private static final String TITLE = "List of Students";

    private View rootView;
    private AdapterStudents adapterStudents;
    private RecyclerView recViewStudentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.frag_list_student, container, false);
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

        getStudentList();
    }

    private void initRecyclerView(){
        LinearLayoutManager studentLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        adapterStudents = new AdapterStudents(null);
        adapterStudents.setItemClickListener(studentListener);
        recViewStudentList = ((RecyclerView) rootView.findViewById(R.id.recView_student_list));
        recViewStudentList.setLayoutManager(studentLayoutManager);
        recViewStudentList.setHasFixedSize(true);
        recViewStudentList.setAdapter(adapterStudents);
    }

    private void getStudentList() {
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Student.class).find(query, new AsyncCallback<BackendlessCollection<Student>>() {
            @Override
            public void handleResponse(BackendlessCollection<Student> response) {
                Toast.makeText(getActivity(), "Loaded " + response.getCurrentPage().size(), Toast.LENGTH_SHORT).show();
                adapterStudents.setData(response.getCurrentPage());
                adapterStudents.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
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
        inflater.inflate(R.menu.frag_list_student, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            replaceFragmentBackStack(new FragAddStudent());
        }
        return super.onOptionsItemSelected(item);
    }

    private AdapterStudents.OnItemClickListener studentListener = new AdapterStudents.OnItemClickListener() {
        @Override
        public void itemClicked(View view, int position, Student student) {
            Bundle bundle = new Bundle();
            bundle.putString("studentName", student.getName());
            bundle.putString("studentSurname", student.getSurname());
            FragStudent fragStudent = new FragStudent();
            fragStudent.setArguments(bundle);
            replaceFragmentBackStack(fragStudent);

            Toast.makeText(getActivity(), "Clicked " + position, Toast.LENGTH_SHORT).show();
        }
    };

}