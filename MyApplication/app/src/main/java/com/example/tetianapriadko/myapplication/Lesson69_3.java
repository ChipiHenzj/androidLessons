package com.example.tetianapriadko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Lesson69_3 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson69_3);

        Log.d(LOG_TAG, "getParcelableExtra");

        Lesson69_2 myObj = (Lesson69_2 ) getIntent().getParcelableExtra(
                Lesson69_2 .class.getCanonicalName());

        Log.d(LOG_TAG, "myObj: " + myObj.s + ", " + myObj.i);
    }
}
