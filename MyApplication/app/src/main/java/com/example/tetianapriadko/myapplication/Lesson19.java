package com.example.tetianapriadko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Lesson19 extends AppCompatActivity  implements View.OnClickListener {

    EditText etNum1;
    EditText etNum2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;

    TextView tvResult;
    String symbol = "";

    public static final int MENU_RESET_ID = 1;
    public static final int MENU_QUIT_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson19);

        etNum1 = ((EditText) findViewById(R.id.etNum1));
        etNum2 = ((EditText) findViewById(R.id.etNum2));

        btnAdd = ((Button) findViewById(R.id.btnAdd));
        btnSub = ((Button) findViewById(R.id.btnSub));
        btnMult = ((Button) findViewById(R.id.btnMult));
        btnDiv = ((Button) findViewById(R.id.btnDiv));

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

        tvResult = (TextView)findViewById(R.id.tvResult);
    }

    @Override
    public void onClick(View v) {

        float num1 = 0;
        float num2 = 0;
        float result = 0;

        if (TextUtils.isEmpty(etNum1.getText().toString())
        || TextUtils.isEmpty(etNum2.getText().toString())) {
        return;
        }

        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());


        switch(v.getId()){
            case R.id.btnAdd:
                symbol = "+";
                result = num1 + num2;
                tvResult.setText(num1 + " " + symbol + " " + num2 + " = " + result);
                break;

            case R.id.btnSub:
                symbol = "-";
                result = num1 - num2;
                tvResult.setText(num1 + " " + symbol+ " " + num2 + " = " + result);
                break;

            case R.id.btnMult:
                symbol = "*";
                result = num1 * num2;
                tvResult.setText(num1 + " " + symbol + " " + num2 + " = " + result);
                break;

            case R.id.btnDiv:
                if(num2 == 0 ){
                    Toast.makeText(this, "Cannot divide on zero", Toast.LENGTH_SHORT).show();
                    etNum2.setText("");
                    tvResult.setText("");
                } else {
                    symbol = "/";
                    result = num1 / num2;
                    tvResult.setText(num1 + " " + symbol + " " + num2 + " = " + result);
                }
                break;
            default:
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_RESET_ID, 0, "Reset");
        menu.add(0, MENU_QUIT_ID, 0, "Quit");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
    switch (item.getItemId()){
        case MENU_QUIT_ID:
            Toast.makeText(this, "Quit", Toast.LENGTH_SHORT).show();
            finish();
            break;
        case MENU_RESET_ID:
            etNum1.setText("");
            etNum2.setText("");
            tvResult.setText("");
            Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
    }


        return super.onOptionsItemSelected(item);
    }

}