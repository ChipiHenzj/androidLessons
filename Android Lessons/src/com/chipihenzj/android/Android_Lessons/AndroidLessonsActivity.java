package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;


/**
 *   Lesson 17.Create View-component working application.
 */

public class AndroidLessonsActivity extends Activity implements View.OnClickListener{

    LinearLayout llMain;
    RadioGroup rgGravity;
    EditText etName;
    Button btnCreate;
    Button btnClear;

    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        llMain = (LinearLayout) findViewById(R.id.llMain);
        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
        etName = (EditText) findViewById(R.id.etName);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnCreate:

            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                            wrapContent, wrapContent);

            int btnGravity = Gravity.LEFT;

            switch (rgGravity.getCheckedRadioButtonId()) {
            case R.id.rbLeft:
                btnGravity = Gravity.LEFT;
                break;
            case R.id.rbCenter:
                btnGravity = Gravity.CENTER_HORIZONTAL;
                break;
            case R.id.rbRight:
                btnGravity = Gravity.RIGHT;
                 break;
            }

            lParams.gravity = btnGravity;

            Button btnNew = new Button(this);
            btnNew.setText(etName.getText().toString());
            llMain.addView(btnNew, lParams);

            break;

        case R.id.btnClear:
            llMain.removeAllViews();
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
        break;
        }
        }

    }












