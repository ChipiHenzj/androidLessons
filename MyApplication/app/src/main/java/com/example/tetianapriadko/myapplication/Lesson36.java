package com.example.tetianapriadko.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class Lesson36 extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    String name[] = { "China", "USA", "Brazil", "Russia", "Japan",
            "Germany", "Egypt", "Italy", "France", "Canada" };

    int people[] = { 1400, 311, 195, 142, 128, 82, 80, 60, 66, 35 };

    String region[] = { "Asia", "America", "America", "Europe", "Asia",
            "Europe", "Africa", "Europe", "Europe", "America" };

    Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving;
    EditText etFunc, etPeople, etRegionPeople;
    RadioGroup rgSort;

    DBHelper dbHelper;
    SQLiteDatabase database;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson36);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = (Button) findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnPeople = (Button) findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);

        btnSort = (Button) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = (Button) findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        etFunc = (EditText) findViewById(R.id.etFunc);
        etPeople = (EditText) findViewById(R.id.etPeople);
        etRegionPeople = (EditText) findViewById(R.id.etRegionPeople);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        dbHelper = new DBHelper(this);
        // подключаемся к базе
        database = dbHelper.getWritableDatabase();

        // проверка существования записей
        Cursor cursor = database.query("mytable", null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            // заполним таблицу
            for (int i = 0; i < 10; i++) {
                contentValues.put("name", name[i]);
                contentValues.put("people", people[i]);
                contentValues.put("region", region[i]);

                Log.d(LOG_TAG, "id = " + database.insert("mytable", null, contentValues));
            }
        }
        cursor.close();
        dbHelper.close();

        // эмулируем нажатие кнопки btnAll
        onClick(btnAll);
    }

    public void onClick(View v) {

        // подключаемся к базе
        database = dbHelper.getWritableDatabase();

        // данные с экрана
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        // курсор
        Cursor cursor = null;

        // определяем нажатую кнопку
        switch (v.getId()) {

            // Все записи
            case R.id.btnAll:
                Log.d(LOG_TAG, "--- All entries ---");
                cursor = database.query("mytable", null, null, null, null, null, null);
                break;

            // Функция
            case R.id.btnFunc:
                Log.d(LOG_TAG, "--- Function " + sFunc + " ---");
                columns = new String[] { sFunc };
                cursor = database.query("mytable", columns, null, null, null, null, null);
                break;

            // Население больше, чем
            case R.id.btnPeople:
                Log.d(LOG_TAG, "--- Population more " + sPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[] { sPeople };
                cursor = database.query("mytable", null, selection, selectionArgs, null, null, null);
                break;

            // Население по региону
            case R.id.btnGroup:
                Log.d(LOG_TAG, "--- Population by region---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                cursor = database.query("mytable", columns, null, null, groupBy, null, null);
                break;

            // Население по региону больше чем
            case R.id.btnHaving:
                Log.d(LOG_TAG, "--- Region with population more " + sRegionPeople + " ---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                cursor = database.query("mytable", columns, null, null, groupBy, having, null);
                break;

            // Сортировка
            case R.id.btnSort:

                // сортировка по
                switch (rgSort.getCheckedRadioButtonId()) {

                    // наименование
                    case R.id.rName:
                        Log.d(LOG_TAG, "--- Sort by name ---");
                        orderBy = "name";
                        break;

                    // население
                    case R.id.rPeople:
                        Log.d(LOG_TAG, "--- Sort by population ---");
                        orderBy = "people";
                        break;

                    // регион
                    case R.id.rRegion:
                        Log.d(LOG_TAG, "--- Sort by region ---");
                        orderBy = "region";
                        break;
                }
                cursor = database.query("mytable", null, null, null, null, null, orderBy);
                break;
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : cursor.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + cursor.getString(cursor.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);

                } while (cursor.moveToNext());
            }
            cursor.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");

        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper (Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            // создаем таблицу с полями
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement," + "name text,"
                    + "people integer," + "region text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
