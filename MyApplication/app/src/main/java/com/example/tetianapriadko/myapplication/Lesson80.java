package com.example.tetianapriadko.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Lesson80 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    Handler h;
    TextView tvInfo;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson80);

        tvInfo = (TextView)findViewById(R.id.tvInfo);
        btnStart = (Button)findViewById(R.id.btnStart);

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {

                tvInfo.setText("Download files: " + msg.what);
                if (msg.what == 10) btnStart.setEnabled(true);
            }
        };

    }

    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:

                btnStart.setEnabled(false);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            downloadFile();
                            h.sendEmptyMessage(i);
                            Log.d(LOG_TAG, "i = " + i);
                        }
                    }
                });

                t.start();
                break;

            case R.id.btnTest:
                Log.d(LOG_TAG, "Test");
                Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    void downloadFile() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
