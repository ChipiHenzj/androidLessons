package com.example.tetianapriadko.myapplication;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lesson61 extends AppCompatActivity {

    final static String LOG_TAG = "myLogs";

    final int DIALOG = 1;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson61);
    }

    public void onclick(View v) {
        showDialog(DIALOG);
    }
    protected Dialog onCreateDialog(int id) {
        Log.d(LOG_TAG, "onCreateDialog");
        if (id == DIALOG) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Present time");
            adb.setMessage(sdf.format(new Date(System.currentTimeMillis())));
            return adb.create();
        }
        return super.onCreateDialog(id);
    }
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        Log.d(LOG_TAG, "onPrepareDialog");
        if (id == DIALOG) {
            ((AlertDialog)dialog).setMessage(sdf.format(new Date(System.currentTimeMillis())));
        }
    }
}


