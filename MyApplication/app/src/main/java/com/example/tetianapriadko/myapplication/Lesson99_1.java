package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class Lesson99_1 extends AppCompatActivity {

    public final static String FILE_NAME = "filename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson99);

        TextView tv = (TextView) findViewById(R.id.tv);

        Intent intent = getIntent();

        String fileName = intent.getStringExtra(FILE_NAME);

        if (!TextUtils.isEmpty(fileName)) {
            tv.setText(fileName);
        }
    }

    public void onClickStart(View v) {
        startService(new Intent(this, Lesson99_2.class));
    }

    public void onClickStop(View v) {
        stopService(new Intent(this, Lesson99_2.class));
    }
}
