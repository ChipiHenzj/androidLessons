package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Lesson92_1 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson92);
    }

    public void onClickStart(View v) {
        startService(new Intent(this, Lesson92_2.class));
    }
    public void onClickStop(View v) {
        stopService(new Intent(this, Lesson92_2.class));
    }


}
