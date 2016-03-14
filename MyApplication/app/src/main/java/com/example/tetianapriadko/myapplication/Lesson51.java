package com.example.tetianapriadko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lesson51 extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;


    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    ListView lvSimple;
    SimpleAdapter sAdapter;
    ArrayList<Map<String, Object>> data;
    Map<String, Object> m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson51_1);

        data = new ArrayList<Map<String, Object>>();

        Map<String, Object> m;

        for (int i = 0; i < 5; i++) {

            m = new HashMap<String, Object>();

            m.put(ATTRIBUTE_NAME_TEXT, "sometext" + i);
            m.put(ATTRIBUTE_NAME_IMAGE, R.mipmap.ic_launcher);

            data.add(m);
        }

        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_IMAGE };

        int[] to = { R.id.tvText, R.id.ivImg};


        sAdapter = new SimpleAdapter(this, data, R.layout.activity_lesson51_2, from, to);

        lvSimple = (ListView)findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);

        registerForContextMenu(lvSimple);
    }

    public void onButtonClick(View v) {
        // создаем новый Map
        m = new HashMap<String, Object>();
        m.put(ATTRIBUTE_NAME_TEXT, "sometext " + (data.size()        ));
        m.put(ATTRIBUTE_NAME_IMAGE, R.mipmap.ic_launcher);

        // добавляем его в коллекцию
        data.add(m);

        // уведомляем, что данные изменились
        sAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CM_DELETE_ID, 0, "Delete item");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == CM_DELETE_ID) {

            // получаем инфу о пункте списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            // удаляем Map из коллекции, используя позицию пункта в списке
            data.remove(acmi.position);

            // уведомляем, что данные изменились
            sAdapter.notifyDataSetChanged();

            return true;
        }
        return super.onContextItemSelected(item);
    }
}
