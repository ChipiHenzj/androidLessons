package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


/**
 *   Lesson 14.1 Menu group order. MenuInflater and xml-menu.
 */

public class AndroidLessonsActivity extends Activity {
    TextView tv;
    CheckBox chb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = (TextView)findViewById(R.id.textView);
        chb = (CheckBox)findViewById(R.id.chbExtMenu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0, 1, 0, "add");
        menu.add(0, 2, 0, "edit");
        menu.add(0, 3, 3, "delete");
        menu.add(1, 4, 1, "copy");
        menu.add(1, 5, 2, "paste");
        menu.add(1, 6, 4, "exit");

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.setGroupVisible(1, chb.isChecked());

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        StringBuilder sb = new StringBuilder();

        sb.append("Item Menu");
        sb.append("\r\n groupId: " + String.valueOf(item.getGroupId()));
        sb.append("\r\n itemId: " + String.valueOf(item.getItemId()));
        sb.append("\r\n order: " + String.valueOf(item.getOrder()));
        sb.append("\r\n title: " + item.getTitle());
        tv.setText(sb.toString());

        return super.onOptionsItemSelected(item);

    }

}



