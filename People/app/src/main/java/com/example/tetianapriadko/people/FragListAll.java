package com.example.tetianapriadko.people;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.example.tetianapriadko.people.adapter.AdapterAll;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragAddSelect;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteFromAll;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragListAll extends Fragment implements AdapterAll.OnItemClickListener,
        AdapterAll.OnItemLongClickListener {

    private static final String TITLE = "List of All";
    private FrameLayout layoutProgress;
    private View rootView;
    private AdapterAll adapterAll;
    private SwipeRefreshLayout refreshAll;
    private Boolean showProgressLayout = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_list_all, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DlgFragAddSelect dlgFragAddSelect = new DlgFragAddSelect();
                dlgFragAddSelect.setTargetFragment(FragListAll.this, 1);
                dlgFragAddSelect.show(getFragmentManager(), dlgFragAddSelect.getDialogTag());
            }
        });

        layoutProgress = ((FrameLayout) rootView.findViewById(R.id.layout_progress));
        layoutProgress.setVisibility(View.GONE);

        initSwipeRefresh(rootView);
        initRecyclerView();
    }

    private void initSwipeRefresh(View rootView){
        refreshAll = ((SwipeRefreshLayout) rootView.findViewById(R.id.refresh_all));
        refreshAll.setOnRefreshListener(refreshListenerAll);
        refreshAll.setColorSchemeResources(R.color.colorPrimary);
        refreshAll.setRefreshing(false);
    }

    private void initRecyclerView() {
        adapterAll = new AdapterAll(null);
        adapterAll.setItemClickListener(this);
        adapterAll.setItemLongClickListener(this);
        RecyclerView recyclerViewAll = ((RecyclerView) rootView.findViewById(R.id.recView_all_list));
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAll.setLayoutManager(linearLayoutManager);
        recyclerViewAll.setHasFixedSize(true);
        recyclerViewAll.setAdapter(adapterAll);
        getStudentList(true);
    }

    private void getStudentList(final Boolean showProgressLayout) {
        if(showProgressLayout){
            layoutProgress.setVisibility(View.VISIBLE);
        } else {
            refreshAll.setRefreshing(true);
        }
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Student.class).find(query, new AsyncCallback<BackendlessCollection<Student>>() {
            @Override
            public void handleResponse(BackendlessCollection<Student> response) {
//                layoutProgress.setVisibility(View.GONE);
//                refreshAll.setRefreshing(false);
                adapterAll.setData(response.getCurrentPage());
                adapterAll.notifyDataSetChanged();
                if(showProgressLayout){
                    getTeacherList(true);
                } else{
                    getTeacherList(false);
                }
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
                if(showProgressLayout){
                    getTeacherList(true);
                } else{
                    getTeacherList(false);
                }
            }
        });
    }

    private void getTeacherList(Boolean showProgressLayout) {
//        if(showProgressLayout){
//            layoutProgress.setVisibility(View.VISIBLE);
//        } else {
//            refreshAll.setRefreshing(true);
//        }
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Teacher.class).find(query, new AsyncCallback<BackendlessCollection<Teacher>>() {
            @Override
            public void handleResponse(BackendlessCollection<Teacher> response) {
                layoutProgress.setVisibility(View.GONE);
                refreshAll.setRefreshing(false);
                adapterAll.setData(response.getCurrentPage());
                adapterAll.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                layoutProgress.setVisibility(View.GONE);
                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void itemClicked(View view, int position) {
        Bundle bundle = new Bundle();
        if (adapterAll.getAllList().get(position) instanceof Student) {
            bundle.putString("studentName", ((Student) adapterAll.getAllList().get(position)).getName());
            bundle.putString("studentSurname", ((Student) adapterAll.getAllList().get(position)).getSurname());
            bundle.putString("studentId", ((Student) adapterAll.getAllList().get(position)).getObjectId());
            FragStudent fragStudent = new FragStudent();
            fragStudent.setArguments(bundle);
            replaceFragmentBackStack(fragStudent);

        } else if (adapterAll.getAllList().get(position) instanceof Teacher) {
            bundle.putString("teacherName", ((Teacher) adapterAll.getAllList().get(position)).getName());
            bundle.putString("teacherSurname", ((Teacher) adapterAll.getAllList().get(position)).getSurname());
            bundle.putString("teacherId", ((Teacher) adapterAll.getAllList().get(position)).getObjectId());
            FragTeacher fragTeacher = new FragTeacher();
            fragTeacher.setArguments(bundle);
            replaceFragmentBackStack(fragTeacher);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String result = data.getStringExtra("Human");
                    if (result.equals("Student")) {
                        replaceFragmentBackStack(new FragAddStudent());
                    } else {
                        replaceFragmentBackStack(new FragAddTeacher());
                    }
                }
                break;
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    if(showProgressLayout){
                        layoutProgress.setVisibility(View.VISIBLE);
                    } else {
                        refreshAll.setRefreshing(true);
                    }
                    if(adapterAll.getAllList().get(data.getIntExtra("position",0)) instanceof Student){
                        Student student
                                = (Student) adapterAll.getAllList().get(data.getIntExtra("position", -1));
                        student.removeAsync(new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                layoutProgress.setVisibility(View.GONE);
                                refreshAll.setRefreshing(false);
                                adapterAll.clearAll();
                                getStudentList(true);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                layoutProgress.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (adapterAll.getAllList().get(data.getIntExtra("position", 0)) instanceof Teacher){
                        Teacher teacher
                                = (Teacher) adapterAll.getAllList().get(data.getIntExtra("position", -1));
                        teacher.removeAsync(new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                layoutProgress.setVisibility(View.GONE);
                                adapterAll.clearAll();
                                getStudentList(true);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                            }
                        });
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void itemLongClicked(View view, int position) {
            DlgFragDeleteFromAll fragDeleteFromAll = new DlgFragDeleteFromAll();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragDeleteFromAll.setArguments(bundle);
            fragDeleteFromAll.setTargetFragment(FragListAll.this, 2);
            fragDeleteFromAll.show(getFragmentManager(), fragDeleteFromAll.getDialogTag());
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListenerAll = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getStudentList(false);
        }
    };
}

