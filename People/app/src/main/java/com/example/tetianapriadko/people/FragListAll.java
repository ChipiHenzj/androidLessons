package com.example.tetianapriadko.people;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.tetianapriadko.people.adapter.AdapterAll;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragAddSelect;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteStudent;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragDeleteTeacher;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;

public class FragListAll extends Fragment implements AdapterAll.OnItemClickListener, AdapterAll.OnItemLongClickListener {

    private static final String TITLE = "List of All";

    View rootView;
    private AdapterAll adapterAll;

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

        initRecyclerView();
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
        getStudentList();
    }

    private void getStudentList() {
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Student.class).find(query, new AsyncCallback<BackendlessCollection<Student>>() {
            @Override
            public void handleResponse(BackendlessCollection<Student> response) {
                adapterAll.setData(response.getCurrentPage());
                adapterAll.notifyDataSetChanged();
                getTeacherList();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), fault.toString(), Toast.LENGTH_SHORT).show();
                getTeacherList();
            }
        });
    }

    private void getTeacherList() {
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery query = new BackendlessDataQuery(queryOptions);
        query.setPageSize(100);
        Backendless.Data.of(Teacher.class).find(query, new AsyncCallback<BackendlessCollection<Teacher>>() {
            @Override
            public void handleResponse(BackendlessCollection<Teacher> response) {
                adapterAll.setData(response.getCurrentPage());
                adapterAll.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
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
            FragStudent fragStudent = new FragStudent();
            fragStudent.setArguments(bundle);
            replaceFragmentBackStack(fragStudent);

        } else if (adapterAll.getAllList().get(position) instanceof Teacher) {
            bundle.putString("teacherName", ((Teacher) adapterAll.getAllList().get(position)).getName());
            bundle.putString("teacherSurname", ((Teacher) adapterAll.getAllList().get(position)).getSurname());
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
//            case 2:
//                if (resultCode == Activity.RESULT_OK) {
//                    String result = data.getStringExtra("Human");
//                    if (result.equals("Student")) {
////                        replaceFragmentBackStack(new FragAddStudent());
//                    } else {
////                        replaceFragmentBackStack(new FragAddTeacher());
//                    }
//                }
//                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void itemLongClicked(View view, int position) {
        if (adapterAll.getAllList().get(position) instanceof Student) {
            DlgFragDeleteStudent studentDelete = new DlgFragDeleteStudent();
            studentDelete.setTargetFragment(FragListAll.this, 2);
            studentDelete.show(getFragmentManager(), studentDelete.getDialogTag());

        } else if (adapterAll.getAllList().get(position) instanceof Teacher) {
            DlgFragDeleteTeacher teacherDelete = new DlgFragDeleteTeacher();
            teacherDelete.setTargetFragment(FragListAll.this, 2);
            teacherDelete.show(getFragmentManager(), teacherDelete.getDialogTag());
        }
    }



}

