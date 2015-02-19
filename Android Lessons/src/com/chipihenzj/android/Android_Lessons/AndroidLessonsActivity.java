package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


/**
 *   Lesson 21. Creating and calling Activity.
 */

public class AndroidLessonsActivity extends Activity implements View.OnClickListener {


    Button btnActTwo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnActTwo = (Button)findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
    case R.id.btnActTwo:
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivity(intent);
        break;
    default:
        break;

        }

    }
}



