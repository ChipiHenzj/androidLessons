package com.example.tetianapriadko.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Lesson10 extends AppCompatActivity {

    TextView tv;
    Button Ok;
    Button Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson10);

        tv = (TextView)findViewById(R.id.tv);
        Ok = (Button)findViewById(R.id.Ok);
        Cancel = (Button)findViewById(R.id.Cancel);

        View.OnClickListener ocl = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                switch(v.getId()){
                    case R.id.Ok:
                        tv.setText("Press button Ok");
                        break;
                    case R.id.Cancel:
                        tv.setText("Press button Cancel");
                        break;
                }
            }
        };

        Ok.setOnClickListener(ocl);
        Cancel.setOnClickListener(ocl);
    }
}
