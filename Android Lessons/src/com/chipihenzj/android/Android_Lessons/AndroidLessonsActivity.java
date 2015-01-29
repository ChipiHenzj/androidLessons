package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



/**
 *   Lesson 15. Context Menu. XML.
 */

public class AndroidLessonsActivity extends Activity {
    TextView tvColor, tvSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        tvColor = (TextView)findViewById(R.id.tvColor);
        tvSize = (TextView)findViewById(R.id.tvSize);

        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);
    }

    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        switch (v.getId()) {
    case R.id.tvColor:
         getMenuInflater().inflate(R.menu.menu, menu);
         menu.setGroupVisible(R.id.group2, false);
         break;
    case R.id.tvSize:
         getMenuInflater().inflate(R.menu.menu, menu);
         menu.setGroupVisible(R.id.group1, false);
         break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
    case R.id.MENU_COLOR_RED:
         tvColor.setTextColor(Color.RED);
         tvColor.setText("Text color = red");
         break;
    case R.id.MENU_COLOR_GREEN:
         tvColor.setTextColor(Color.GREEN);
         tvColor.setText("Text color = green");
         break;
    case R.id.MENU_COLOR_BLUE:
         tvColor.setTextColor(Color.BLUE);
         tvColor.setText("Text color = blue");
         break;
    case R.id.MENU_SIZE_22:
         tvSize.setTextSize(22);
         tvSize.setText("Text size = 22");
         break;
    case R.id.MENU_SIZE_26:
         tvSize.setTextSize(26);
         tvSize.setText("Text size = 26");
         break;
    case R.id.MENU_SIZE_30:
         tvSize.setTextSize(30);
         tvSize.setText("Text size = 30");
         break;
         }
        return super.onContextItemSelected(item);
    }

}











