package com.chipihenzj.android.Android_Lessons;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;



/** Lesson 43. Single and multiple selection in ListView
 *
 */

public class AndroidLessonsActivity extends Activity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    ListView lvMain;
    String[] names;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lvMain = (ListView) findViewById(R.id.lvMain);

        // устанавливаем режим выбора пунктов списка
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Создаем адаптер, используя массив из файла ресурсов
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.names,
                android.R.layout.simple_list_item_multiple_choice);

        lvMain.setAdapter(adapter);

        Button btnChecked = (Button) findViewById(R.id.btnChecked);
        btnChecked.setOnClickListener(this);

        // получаем массив из файла ресурсов
        names = getResources().getStringArray(R.array.names);
    }

    public void onClick(View arg0) {

        // пишем в лог выделенный элемент
//        Log.d(LOG_TAG, "checked: " + names[lvMain.getCheckedItemPosition()]);


        Log.d(LOG_TAG, "checked: ");
        SparseBooleanArray sbArray = lvMain.getCheckedItemPositions();
        for (int i = 0; i < sbArray.size(); i++) {
            int key = sbArray.keyAt(i);
            if (sbArray.get(key))
                Log.d(LOG_TAG, names[key]);
        }
    }
}






















