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
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.tetianapriadko.people.adapter.AdapterTeachers;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteTeachList;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragListTeacher extends Fragment {

    private static final String TITLE = "List of Teachers";

    private FrameLayout layoutProgress;
    private View rootView;
    private RecyclerView recViewTeacherList;
    private AdapterTeachers adapterTeachers;
    private SwipeRefreshLayout refreshTeacher;
    private Boolean showProgressLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_list_teacher, container, false);
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

        layoutProgress = ((FrameLayout) rootView.findViewById(R.id.layout_progress));
        layoutProgress.setVisibility(View.GONE);

        initRecyclerView();
        iniSwipeRefresh(rootView);
        getTeacherList(true);
    }

    public void iniSwipeRefresh(View rootView){
        refreshTeacher = ((SwipeRefreshLayout) rootView.findViewById(R.id.refresh_teacher));
        refreshTeacher.setOnRefreshListener(refreshListener);
        refreshTeacher.setColorSchemeResources(R.color.colorPrimary);
        refreshTeacher.setRefreshing(false);
    }

    private void initRecyclerView() {
        LinearLayoutManager studentLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        adapterTeachers = new AdapterTeachers(null);
        adapterTeachers.setItemClickListener(teacherListener);
        adapterTeachers.setItemLongClickListener(teacherLongListener);
        recViewTeacherList = ((RecyclerView) rootView.findViewById(R.id.recView_teacher_list));
        recViewTeacherList.setLayoutManager(studentLayoutManager);
        recViewTeacherList.setHasFixedSize(true);
        recViewTeacherList.setAdapter(adapterTeachers);
    }

    private void getTeacherList(Boolean showProgressLayout) {
        if(showProgressLayout){
            layoutProgress.setVisibility(View.VISIBLE);
        } else {
            refreshTeacher.setRefreshing(true);
        }
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Teacher.class).find(query,
                new AsyncCallback<BackendlessCollection<Teacher>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<Teacher> response) {
                        layoutProgress.setVisibility(View.GONE);
                        refreshTeacher.setRefreshing(false);
                        adapterTeachers.setData(response.getCurrentPage());
                        adapterTeachers.notifyDataSetChanged();
                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                        layoutProgress.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
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

    private AdapterTeachers.OnItemClickListener teacherListener =
            new AdapterTeachers.OnItemClickListener() {
        @Override
        public void itemClicked(View view, int position, Teacher teacher) {
            Bundle bundle = new Bundle();
            bundle.putString("teacherName", teacher.getName());
            bundle.putString("teacherSurname", teacher.getSurname());
            bundle.putString("teacherId", teacher.getObjectId());
            FragTeacher fragTeacher = new FragTeacher();
            fragTeacher.setArguments(bundle);
            replaceFragmentBackStack(fragTeacher);
        }
    };

    private AdapterTeachers.OnItemLongClickListener teacherLongListener =
            new AdapterTeachers.OnItemLongClickListener() {
                @Override
                public void itemLongClicked(View view, int position, Teacher teacher) {
                    DlgFragDeleteTeachList teacherDeleteList = new DlgFragDeleteTeachList();
                    Bundle bundlePosition = new Bundle();
                    bundlePosition.putInt("positionTeacher", position);
                    teacherDeleteList.setArguments(bundlePosition);
                    teacherDeleteList.setTargetFragment(FragListTeacher.this, 1);
                    teacherDeleteList.show(getFragmentManager(), teacherDeleteList.getDialogTag());
                }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                switch (requestCode) {
                    case 1:
                        if(showProgressLayout){
                            layoutProgress.setVisibility(View.VISIBLE);
                        } else {
                            refreshTeacher.setRefreshing(true);
                        }
                        Teacher teacher
                                = adapterTeachers.getTeachers().get(data.getIntExtra("positionTeacher", -1));
                        teacher.removeAsync(new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                layoutProgress.setVisibility(View.GONE);
                                refreshTeacher.setRefreshing(false);
                                getTeacherList(false);
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
            getTeacherList(false);
        }
    };
}
