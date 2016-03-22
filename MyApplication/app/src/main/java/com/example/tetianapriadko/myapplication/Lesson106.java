package com.example.tetianapriadko.myapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Lesson106 extends AppCompatActivity implements Lesson106_2.onSomeEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson106);

        Fragment frag2 = new Lesson106_2();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.CONTAINER_VIEW_ID, frag2);
        ft.commit();
    }

//    public void onClick(View v) {
//        Fragment frag1 = getSupportFragmentManager().findFragmentById(R.id.fragment1);
//        ((TextView) frag1.getView().findViewById(R.id.textView))
//                .setText("Access to Fragment 1 from Activity");
//
//        Fragment frag2 = getSupportFragmentManager().findFragmentById(R.id.CONTAINER_VIEW_ID);
//        ((TextView) frag2.getView().findViewById(R.id.textView))
//                .setText("Access to Fragment 2 from Activity");
//    }


    @Override
    public void someEvent(String s) {
        Fragment frag1 = getSupportFragmentManager().findFragmentById(R.id.fragment1);
        ((TextView)frag1.getView().findViewById(R.id.textView)).setText("Text from Fragment 2:" + s);
    }
}
