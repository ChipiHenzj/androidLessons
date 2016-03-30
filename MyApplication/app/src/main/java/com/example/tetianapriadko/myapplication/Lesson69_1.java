package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Lesson69_1 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson69);
    }

    public void onclick(View v) {


//        Lesson69_2 myObj = new Lesson69_2("text", 1);
//
//        Intent intent = new Intent(this, Lesson69_3.class);
//        intent.putExtra(Lesson69_2.class.getCanonicalName(), myObj);
//
//        Log.d(LOG_TAG, "startActivity");
//        startActivity(intent);


    Intent intent = new Intent(this, Lesson69_3.class);
    intent.putExtra("Lesson69_2", new Lesson69_2("Muris", "Long", "White", "Fluffy"));
    startActivity(intent);

    }





}
