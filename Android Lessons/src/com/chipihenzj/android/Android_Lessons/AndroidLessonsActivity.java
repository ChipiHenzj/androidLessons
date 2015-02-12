package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


/**
 *   Lesson 19.Write a simple calculator.
 */

public class AndroidLessonsActivity extends Activity implements OnClickListener {

    final int MENU_RESET_ID = 1;
    final int MENU_QUIT_ID = 2;

    EditText etNum1;
    EditText etNum2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;

    TextView tvResult;

    String oper = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

        tvResult = (TextView) findViewById(R.id.tvResult);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnSub = (Button) findViewById(R.id.btnSub);
        btnSub.setOnClickListener(this);

        btnMult = (Button) findViewById(R.id.btnMult);
        btnMult.setOnClickListener(this);

        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        float num1;
        float num2;
        float result = 0;

        if (TextUtils.isEmpty(etNum1.getText().toString()) || TextUtils.isEmpty(etNum2.getText().toString())) {
            return;
        }

        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());


        switch (v.getId()) {
    case R.id.btnAdd:
        oper = "+";
        result = num1 + num2;
        break;
    case R.id.btnSub:
        oper = "-";
        result = num1 - num2;
        break;
    case R.id.btnMult:
        oper = "*";
        result = num1 * num2;
        break;
    case R.id.btnDiv:
        oper = "/";
        result = num1 / num2;
        break;
    default:
        break;
        }

        tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_RESET_ID, 0, "Reset");
        menu.add(0, MENU_QUIT_ID, 0, "Quit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
    case MENU_RESET_ID:
        etNum1.setText("");
        etNum2.setText("");
        tvResult.setText("");
        break;

    case MENU_QUIT_ID:
        finish();
        break;
        }

        return super.onOptionsItemSelected(item);
    }


}








