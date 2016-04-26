package com.example.tetianapriadko.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}



//    protected void replaceFragmentBackStack(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(R.id.frag_container, fragment)
//                .addToBackStack("frag")
//                .commit();
//    }
//
//    protected void addFragmentBackStack(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
////              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .add(R.id.frag_container, fragment)
//                .addToBackStack("frag")
//                .commit();
//    }