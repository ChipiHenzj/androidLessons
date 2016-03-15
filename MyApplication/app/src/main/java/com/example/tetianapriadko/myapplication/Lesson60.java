package com.example.tetianapriadko.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Lesson60 extends AppCompatActivity {

    final int DIALOG_EXIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson60);
    }

    public void onBackPressed() {
        showDialog(DIALOG_EXIT);
    }

    public void onclick(View v) {
        showDialog(DIALOG_EXIT);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);

            adb.setTitle(R.string.exit);

            adb.setMessage(R.string.save_data);

            adb.setIcon(android.R.drawable.ic_dialog_info);

            adb.setPositiveButton(R.string.yes, myClickListener);

            adb.setNegativeButton(R.string.no, myClickListener);

            adb.setNeutralButton(R.string.cancel, myClickListener);

            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {

                case Dialog.BUTTON_POSITIVE:
                    saveData();
                    finish();
                    break;

                case Dialog.BUTTON_NEGATIVE:
                    finish();
                    break;

                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

    public void saveData() {
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }
}
