package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 *   Lesson 32.Writing a simple browser
 */

public class AndroidLessonsActivity extends Activity  {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        (findViewById(R.id.btnWeb)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yandex.ru")));
            }
        });
    }
}










