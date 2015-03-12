package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 *   Lesson 31.Why have there Intent attribute data. What is Uri. Call the system applications
 */

public class AndroidLessonsActivity extends Activity implements View.OnClickListener {

    Button btnWeb;
    Button btnMap;
    Button btnCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnWeb = (Button)findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(this);

        btnMap = (Button)findViewById(R.id.btnMap);
        btnMap.setOnClickListener(this);

        btnCall = (Button)findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
    case R.id.btnWeb:
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com"));
        startActivity(intent);
        break;
    case R.id.btnMap:
        intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:55.754283,37.62002"));
        startActivity(intent);
        break;
    case R.id.btnCall:
        intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:12345"));
        startActivity(intent);
        break;
        }


    }


}





