package com.chipihenzj.android.Android_Lessons;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class AlignActivity extends Activity implements View.OnClickListener{

    Button btnCenter;
    Button btnRight;
    Button btnLeft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.align);

        btnCenter = (Button)findViewById(R.id.btnCenter);
        btnCenter.setOnClickListener(this);

        btnRight = (Button)findViewById(R.id.btnRight);
        btnRight.setOnClickListener(this);

        btnLeft = (Button)findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
    case R.id.btnCenter:
        intent.putExtra("alignment", Gravity.CENTER);
        break;
    case R.id.btnRight:
        intent.putExtra("alignment", Gravity.RIGHT);
        break;
    case R.id.btnLeft:
        intent.putExtra("alignment", Gravity.LEFT);
        break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
