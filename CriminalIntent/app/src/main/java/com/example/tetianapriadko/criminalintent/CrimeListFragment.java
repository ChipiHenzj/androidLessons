package com.example.tetianapriadko.criminalintent;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CrimeListFragment extends ListFragment{

    private ArrayList<Crime> mCrimes;
    private static final String TAG = "CrimeListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        ArrayAdapter<Crime> adapter =
                new ArrayAdapter<Crime>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        mCrimes);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime c = (Crime)(getListAdapter()).getItem(position);
        Toast.makeText(getActivity(), c.getTitle() + "was clicked", Toast.LENGTH_SHORT).show();
        Log.d(TAG, c.getTitle() + " was clicked");
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            return null;
        }
    }

}
