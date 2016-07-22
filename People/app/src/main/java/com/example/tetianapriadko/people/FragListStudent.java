package com.example.tetianapriadko.people;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.tetianapriadko.people.adapter.AdapterStudents;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteStudList;
import com.example.tetianapriadko.people.structure.Student;

import java.util.ArrayList;
import java.util.List;

public class FragListStudent extends Fragment {

    private static final String TITLE = "List of Students";

    private FrameLayout layoutProgress;
    private View rootView;
    private AdapterStudents adapterStudents;
    private RecyclerView recViewStudentList;
    private SwipeRefreshLayout refreshStudent;
    private Boolean showProgressLayout = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_list_student, container, false);
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

        layoutProgress = ((FrameLayout)rootView.findViewById(R.id.layout_progress));
        layoutProgress.setVisibility(View.GONE);

        initRecyclerView();
        initRefreshLayout(rootView);

        getStudentList(true);
    }

    private void initRefreshLayout(View rootView) {
        refreshStudent = ((SwipeRefreshLayout) rootView.findViewById(R.id.refresh_student));
        refreshStudent.setOnRefreshListener(refreshListener);
        refreshStudent.setColorSchemeResources(R.color.colorPrimary);
        refreshStudent.setRefreshing(false);
    }

    private void initRecyclerView() {
        LinearLayoutManager studentLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        adapterStudents = new AdapterStudents(null);
        adapterStudents.setItemClickListener(studentListener);
        adapterStudents.setItemLongClickListener(studentLongListener);
        recViewStudentList = ((RecyclerView) rootView.findViewById(R.id.recView_student_list));
        recViewStudentList.setLayoutManager(studentLayoutManager);
        recViewStudentList.setHasFixedSize(true);
        recViewStudentList.setAdapter(adapterStudents);
    }

    private void getStudentList(Boolean showProgressLayout) {
        if (showProgressLayout){
            layoutProgress.setVisibility(View.VISIBLE);
        } else {
            refreshStudent.setRefreshing(true);
        }
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Student.class).find(query, new AsyncCallback<BackendlessCollection<Student>>() {
            @Override
            public void handleResponse(BackendlessCollection<Student> response) {
                layoutProgress.setVisibility(View.GONE);
                refreshStudent.setRefreshing(false);
                adapterStudents.setData(response.getCurrentPage());
                adapterStudents.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
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
            bundle.putString("studentId", student.getObjectId());
            FragStudent fragStudent = new FragStudent();
            fragStudent.setArguments(bundle);
            replaceFragmentBackStack(fragStudent);
        }
    };

    private AdapterStudents.OnItemLongClickListener studentLongListener
            = new AdapterStudents.OnItemLongClickListener() {
        @Override
        public void itemLongClicked(View view, int position, Student student) {
            DlgFragDeleteStudList studentDelete = new DlgFragDeleteStudList();
            Bundle bundle = new Bundle();
            bundle.putInt("positionStudent", position);
            studentDelete.setArguments(bundle);
            studentDelete.setTargetFragment(FragListStudent.this, 1);
            studentDelete.show(getFragmentManager(), studentDelete.getDialogTag());
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        if (showProgressLayout){
                            layoutProgress.setVisibility(View.VISIBLE);
                        } else {
                            refreshStudent.setRefreshing(true);
                        }
                        Student student
                                = adapterStudents.getStudents().get(data.getIntExtra("positionStudent", -1));

                        student.removeAsync(new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                layoutProgress.setVisibility(View.GONE);
                                refreshStudent.setRefreshing(false);
                                getStudentList(true);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                layoutProgress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
                break;
            case Activity.RESULT_CANCELED:
                switch (requestCode) {
                    case 1:
                        Toast.makeText(getActivity(), "Result Cancel", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getStudentList(false);
        }
    };

}