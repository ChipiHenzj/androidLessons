package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lesson100_1 extends AppCompatActivity implements View.OnClickListener{

    Button srv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson100_1);

        srv = (Button)findViewById(R.id.srv);
        srv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        startService(new Intent(this, Lesson100_2.class)
                .putExtra("time", 3).putExtra("label", "Call 1"));

        startService(new Intent(this, Lesson100_2.class)
                .putExtra("time", 1).putExtra("label", "Call 2"));

        startService(new Intent(this, Lesson100_2.class)
                .putExtra("time", 4).putExtra("label", "Call 3"));

    }
}
