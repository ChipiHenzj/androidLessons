package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Lesson93_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson93);
    }

    public void onClickStart(View v) {
        startService(new Intent(this, Lesson93_2.class).putExtra("time", 7));
        startService(new Intent(this, Lesson93_2.class).putExtra("time", 2));
        startService(new Intent(this, Lesson93_2.class).putExtra("time", 4));
    }

}
