package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 *   Lesson 29.Call Activity and get the result. Method startActivityForResult
 */

public class AndroidLessonsActivity extends Activity implements View.OnClickListener {

    TextView tvName;
    Button btnName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tvName = (TextView)findViewById(R.id.tvName);
        btnName = (Button)findViewById(R.id.btnName);
        btnName.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NameActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name = data.getStringExtra("name");
        tvName.setText("Your name is " + name);
    }
}




