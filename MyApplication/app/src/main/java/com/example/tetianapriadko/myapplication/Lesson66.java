package com.example.tetianapriadko.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Lesson66 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    final int DIALOG = 1;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson66);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG) {

            Log.d(LOG_TAG, "Create");

            AlertDialog.Builder adb = new AlertDialog.Builder(this);

            adb.setTitle("Title");
            adb.setMessage("Message");
            adb.setPositiveButton("OK", null);
            dialog = adb.create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    Log.d(LOG_TAG, "Show");
                }
            });


            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    Log.d(LOG_TAG, "Cancel");
                }
            });

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    Log.d(LOG_TAG, "Dismiss");
                }
            });
            return dialog;
        }
        return super.onCreateDialog(id);
    }

    public void onclick(View v) {
        showDialog(DIALOG);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            public void run() {
                method1();
            }
        }, 2000);
        h.postDelayed(new Runnable() {
            public void run() {
                method2();
            }
        }, 4000);

    }

    void method1() {
//        dialog.dismiss();
//        dialog.cancel();
//        dialog.hide();
//        dismissDialog(DIALOG);
        removeDialog(DIALOG);
    }

    void method2() {
//        dialog.cancel();
        showDialog(DIALOG);
    }
}
