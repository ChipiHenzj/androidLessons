package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 *   Lesson 30.More information about onActivityResult. Why requestCode and resultCode
 */

public class AndroidLessonsActivity extends Activity implements View.OnClickListener {

    TextView tvText;
    Button btnColor;
    Button btnAlign;

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tvText = (TextView)findViewById(R.id.tvText);

        btnColor= (Button)findViewById(R.id.btnColor);
        btnColor.setOnClickListener(this);

        btnAlign = (Button)findViewById(R.id.btnAlign);
        btnAlign.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
    case R.id.btnColor:
        intent = new Intent(this, ColorActivity.class);
        startActivityForResult(intent, REQUEST_CODE_COLOR);
        break;
    case R.id.btnAlign:
        intent = new Intent(this, AlignActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ALIGN);
        break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (resultCode == RESULT_OK) {
            switch (requestCode) {
        case REQUEST_CODE_COLOR:
            int color = data.getIntExtra("color", Color.WHITE);
            tvText.setTextColor(color);
            break;
        case REQUEST_CODE_ALIGN:
            int align = data.getIntExtra("alignment", Gravity.LEFT);
            tvText.setGravity(align);
            break;
            }

        }
        else {

            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
        }
    }
}





