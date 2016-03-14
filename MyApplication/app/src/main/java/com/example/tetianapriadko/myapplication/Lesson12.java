package com.example.tetianapriadko.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Lesson12 extends AppCompatActivity implements View.OnClickListener{

    TextView tvOut;
    Button btnOk;
    Button btnCancel;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson12);

        Log.d(TAG, "Find View-items");
        tvOut = (TextView)findViewById(R.id.tvOut);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        Log.d(TAG, "Assign listener for buttons");
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "by id to determine the button that caused the listener");
        switch(v.getId()){
            case R.id.btnOk:
                Log.d(TAG, "Press button ОК");
                tvOut.setText("Press button Ok");
                Toast.makeText(this, "Press button ОК", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnCancel:
                Log.d(TAG, "Press button Cancel");
                tvOut.setText("Press button Cancel");
                Toast.makeText(this, "Press button Cancel", Toast.LENGTH_LONG).show();
                break;
        }


    }
}
