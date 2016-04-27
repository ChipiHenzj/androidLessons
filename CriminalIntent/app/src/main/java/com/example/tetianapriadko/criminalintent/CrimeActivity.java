package com.example.tetianapriadko.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
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