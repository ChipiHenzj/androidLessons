package com.example.tetianapriadko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lesson42 extends AppCompatActivity {

    String[] names = { "Maris", "Agita", "Ricards", "Janis", "Agnese", "Laima", "Viesturs",
            "Lauris", "Guntars", "Arvis", "Gatis", "Gita", "Davis", "Kaspars", "Laila",
             "Kristaps", "Laura", "Liga", "Liene", "Inga", "Martins", "Zanda", "Sandris"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson42);
        
        ListView lvMain = (ListView) findViewById(R.id.lvMain);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.activity_lesson42_1, names);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        lvMain.setAdapter(adapter);
    }
}
