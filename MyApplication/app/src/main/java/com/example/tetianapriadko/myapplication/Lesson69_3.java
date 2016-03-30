package com.example.tetianapriadko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Lesson69_3 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lesson69_3);
//
//        Log.d(LOG_TAG, "getParcelableExtra");
//
//        Lesson69_2 myObj = (Lesson69_2 ) getIntent().getParcelableExtra(
//                Lesson69_2 .class.getCanonicalName());
//
//        Log.d(LOG_TAG, "myObj: " + myObj.s + ", " + myObj.i);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson69_3);

        TextView tvName = (TextView) findViewById(R.id.textView1);
        TextView tvWhiskers = (TextView) findViewById(R.id.textView2);
        TextView tvPaws = (TextView) findViewById(R.id.textView3);
        TextView tvTail = (TextView) findViewById(R.id.textView4);

        String name = "";
        String whiskers = "";
        String paws = "";
        String tail = "";

        Lesson69_2 myCat = (Lesson69_2) getIntent().getParcelableExtra("Lesson69_2");

        name = myCat.getCatName();
        whiskers = myCat.getWhiskers();
        paws = myCat.getPaws();
        tail = myCat.getTail();

        tvName.setText("Cat name: " + name);
        tvWhiskers.setText("Whiskers: " + whiskers);
        tvPaws.setText("Paws: " + paws);
        tvTail.setText("Tail: " + tail);
    }

}
