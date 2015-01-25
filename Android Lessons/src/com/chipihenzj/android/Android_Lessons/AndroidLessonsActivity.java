package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AndroidLessonsActivity extends Activity implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        action();
    }

    private void action() {

        /**
         * Lesson 12. Toast and Log.
         */
        Button buttonOk = (Button)findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(this);

        Button buttonCancel = (Button)findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:
                Log.e("MyLog", "Button OK");
                Toast.makeText(getApplicationContext(), "Button OK", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_cancel:
                Log.e("MyLog", "Button Cancel");
                Toast.makeText(getApplicationContext(), "Button Cancel", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
