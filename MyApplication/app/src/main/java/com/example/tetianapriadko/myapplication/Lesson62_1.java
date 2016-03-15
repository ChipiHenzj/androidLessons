package com.example.tetianapriadko.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

public class Lesson62_1 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final int DIALOG_ITEMS = 1;
    final int DIALOG_ADAPTER = 2;
    final int DIALOG_CURSOR = 3;

    int cnt = 0;

    Lesson62_2 db;
    Cursor cursor;
    String data[] = { "one", "two", "three", "four" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson62);

        // открываем подключение к БД
        db = new Lesson62_2(this);
        db.open();
        cursor = db.getAllData();
        startManagingCursor(cursor);
    }

    public void onclick(View v) {
        changeCount();
        switch (v.getId()) {
            case R.id.btnItems:
                showDialog(DIALOG_ITEMS);
                break;
            case R.id.btnAdapter:
                showDialog(DIALOG_ADAPTER);
                break;
            case R.id.btnCursor:
                showDialog(DIALOG_CURSOR);
                break;
            default:
                break;
        }
    }
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        switch (id) {

            // массив
            case DIALOG_ITEMS:
                adb.setTitle(R.string.items);
                adb.setItems(data, myClickListener);
                break;

            // адаптер
            case DIALOG_ADAPTER:
                adb.setTitle(R.string.adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_item, data);
                adb.setAdapter(adapter, myClickListener);
                break;

            // курсор
            case DIALOG_CURSOR:
                adb.setTitle(R.string.cursor);
                adb.setCursor(cursor, myClickListener, Lesson62_2.COLUMN_TXT);
                break;
        }
        return adb.create();
    }
    protected void onPrepareDialog(int id, Dialog dialog) {

        // получаем доступ к адаптеру списка диалога
        AlertDialog aDialog = (AlertDialog) dialog;
        ListAdapter lAdapter = aDialog.getListView().getAdapter();

        switch (id) {
            case DIALOG_ITEMS:
            case DIALOG_ADAPTER:

                    // проверка возможности преобразования
                if (lAdapter instanceof BaseAdapter) {

                    // преобразование и вызов метода-уведомления о новых данных
                    BaseAdapter bAdapter = (BaseAdapter) lAdapter;
                    bAdapter.notifyDataSetChanged();
                }
                break;
            case DIALOG_CURSOR:
                break;
            default:
                break;
        }
    }

    // обработчик нажатия на пункт списка диалога
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {

            // выводим в лог позицию нажатого элемента
            Log.d(LOG_TAG, "which = " + which);
        }
    };


    // меняем значение счетчика
    void changeCount() {
        cnt++;

        // обновляем массив
        data[3] = String.valueOf(cnt);

        // обновляем БД
        db.changeRec(4, String.valueOf(cnt));
        cursor.requery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
