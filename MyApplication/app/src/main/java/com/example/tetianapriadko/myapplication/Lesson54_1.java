package com.example.tetianapriadko.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Lesson54_1 extends AppCompatActivity {

    ArrayList<Lesson54_2> products = new ArrayList<Lesson54_2>();
    Lesson54_3  boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson54_1);


        // создаем адаптер
        boxAdapter = new Lesson54_3(this, products);

        // генерируем данные для адаптера
        for (int i = 1; i <= 20; i++) {
            products.add(new Lesson54_2("Product " + i, i * 1000,
                    R.mipmap.ic_launcher, false));
        }

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);
    }

    // выводим информацию о корзине
    public void showResult(View v) {
        String result = "Items in box:";
        for (Lesson54_2 p : boxAdapter.getBox()) {
            if (p.box)
                result += "\n" + p.name;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }
}


