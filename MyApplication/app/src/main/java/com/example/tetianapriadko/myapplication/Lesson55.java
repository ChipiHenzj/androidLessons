package com.example.tetianapriadko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Lesson55 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    String[] data = {"one", "two", "three", "four", "five"};

    ListView lvMain;

    ArrayAdapter<String> adapter;

    View header1;
    View header2;
    View footer1;
    View footer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson55_1);

        lvMain = (ListView) findViewById(R.id.lvMain);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        // создаем Header и Footer
        header1 = createHeader("header 1");
        header2 = createHeader("header 2");
        footer1 = createFooter("footer 1");
        footer2 = createFooter("footer 2");
        fillList();
    }

    // формирование списка
    void fillList() {
        lvMain.addHeaderView(header1);
        lvMain.addHeaderView(header2, "some text for header 2", false);
        lvMain.addFooterView(footer1);
        lvMain.addFooterView(footer2, "some text for footer 2", false);
        lvMain.setAdapter(adapter);
    }


    // нажатие кнопки
    public void onclick(View v) {
//        lvMain.removeHeaderView(header2);
//        lvMain.removeFooterView(footer2);

        Object obj;

        HeaderViewListAdapter hvlAdapter = (HeaderViewListAdapter) lvMain.getAdapter();

        obj = hvlAdapter.getItem(1);
        Toast.makeText(this, "hvlAdapter.getItem(1) = " + obj.toString(), Toast.LENGTH_LONG).show();

        obj = hvlAdapter.getItem(4);
        Toast.makeText(this, "hvlAdapter.getItem(4) = " + obj.toString(), Toast.LENGTH_LONG).show();

        ArrayAdapter<String> alAdapter = (ArrayAdapter<String>) hvlAdapter.getWrappedAdapter();

        obj = alAdapter.getItem(1);
        Toast.makeText(this, "alAdapter.getItem(1) = " + obj.toString(), Toast.LENGTH_LONG).show();

        obj = alAdapter.getItem(4);
        Toast.makeText(this, "alAdapter.getItem(4) = " + obj.toString(), Toast.LENGTH_LONG).show();
    }

    // создание Header
    View createHeader(String text) {
        View v = getLayoutInflater().inflate(R.layout.activity_lesson55_2, null);
        ((TextView)v.findViewById(R.id.tvText)).setText(text);
        return v;
    }

    // создание Footer
    View createFooter(String text) {
        View v = getLayoutInflater().inflate(R.layout.activity_lesson55_3, null);
        ((TextView)v.findViewById(R.id.tvText)).setText(text);
        return v;
    }
}
