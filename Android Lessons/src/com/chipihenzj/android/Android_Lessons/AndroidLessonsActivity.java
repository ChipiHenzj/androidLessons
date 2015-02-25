package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 *   Lesson 26. Intent Filter - Practice
 */

public class AndroidLessonsActivity extends Activity implements View.OnClickListener{

    Button btnTime;
    Button btnDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnTime  = (Button)findViewById(R.id.btnTime);
        btnTime.setOnClickListener(this);

        btnDate = (Button)findViewById(R.id.btnDate);
        btnDate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()){
    case R.id.btnTime:
        intent = new Intent("startandroid.intent.action.showtime");
        startActivity(intent);
        break;
    case R.id.btnDate:
        intent = new Intent("startandroid.intent.action.showdate");
        startActivity(intent);
        break;

        }

    }
}



