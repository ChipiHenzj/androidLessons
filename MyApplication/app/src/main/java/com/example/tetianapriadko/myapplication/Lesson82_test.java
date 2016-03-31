package com.example.tetianapriadko.myapplication;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Lesson82_test extends AppCompatActivity {

    static final int STATUS_NONE = 0;
    static final int STATUS_CONNECTING = 1;
    static final int STATUS_CONNECTED = 2;
    static final int STATUS_DOWNLOAD_START = 3;
    static final int STATUS_DOWNLOAD_FILE = 4;
    static final int STATUS_DOWNLOAD_END = 5;
    static final int STATUS_DOWNLOAD_NONE = 6;

    TextView tvStatus;
    Button btnConnect;
    ProgressBar pbDownload;

    static myHandler h;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson82);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        pbDownload = (ProgressBar)findViewById(R.id.pbDownload);

        if(h == null) {
            h = new myHandler(this);
        } else {
            h.setTarget(this);
        }

        h.sendEmptyMessage(STATUS_NONE);
    }


    public void onclick(View v) {

        Thread t = new Thread(new Runnable() {

            Message msg;
            byte[] file;
            Random rand = new Random();

            public void run() {
                try {
                    // устанавливаем подключение
                    h.sendEmptyMessage(STATUS_CONNECTING);
                    TimeUnit.SECONDS.sleep(1);

                    // подключение установлено
                    h.sendEmptyMessage(STATUS_CONNECTED);

                    // определяем кол-во файлов
                    TimeUnit.SECONDS.sleep(1);
                    int filesCount = rand.nextInt(5);

                    if (filesCount == 0) {
                        // сообщаем, что файлов для загрузки нет
                        h.sendEmptyMessage(STATUS_DOWNLOAD_NONE);
                        // и отключаемся
                        TimeUnit.MILLISECONDS.sleep(1500);
                        h.sendEmptyMessage(STATUS_NONE);
                        return;
                    }

                    // загрузка начинается
                    // создаем сообщение, с информацией о количестве файлов
                    msg = h.obtainMessage(STATUS_DOWNLOAD_START, filesCount, 0);
                    // отправляем
                    h.sendMessage(msg);

                    for (int i = 1; i <= filesCount; i++) {
                        // загружается файл
                        file = downloadFile();
                        // создаем сообщение с информацией о порядковом номере
                        // файла,
                        // кол-вом оставшихся и самим файлом
                        msg = h.obtainMessage(STATUS_DOWNLOAD_FILE, i,
                                filesCount - i, file);
                        // отправляем
                        h.sendMessage(msg);
                    }

                    // загрузка завершена
                    h.sendEmptyMessage(STATUS_DOWNLOAD_END);

                    // отключаемся
                    TimeUnit.MILLISECONDS.sleep(1500);
                    h.sendEmptyMessage(STATUS_NONE);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public byte[] downloadFile() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new byte[1024];
    }

    private static class myHandler extends Handler {

        private WeakReference<Lesson82_test> mTarget;

        myHandler(Lesson82_test target) {
            mTarget = new WeakReference<Lesson82_test>(target);
        }

        public void setTarget(Lesson82_test target) {
            mTarget.clear();
            mTarget = new WeakReference<Lesson82_test>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            Lesson82_test activity = mTarget.get();

            switch (msg.what) {
                case STATUS_NONE:
                    activity.btnConnect.setEnabled(true);
                    activity.tvStatus.setText("Not connected");
                    activity.pbDownload.setVisibility(View.GONE);
                    break;
                case STATUS_CONNECTING:
                    activity.btnConnect.setEnabled(false);
                    activity.tvStatus.setText("Connecting");
                    break;
                case STATUS_CONNECTED:
                    activity. tvStatus.setText("Connected");
                    break;
                case STATUS_DOWNLOAD_START:
                    activity. tvStatus.setText("Start download " + msg.arg1 + " files");
                    activity. pbDownload.setMax(msg.arg1);
                    activity. pbDownload.setProgress(0);
                    activity. pbDownload.setVisibility(View.VISIBLE);
                    break;
                case STATUS_DOWNLOAD_FILE:
                    activity. tvStatus.setText("Downloading. Left " + msg.arg2 + " files");
                    activity. pbDownload.setProgress(msg.arg1);
                    break;
                case STATUS_DOWNLOAD_END:
                    activity. tvStatus.setText("Download complete!");
                    break;
                case STATUS_DOWNLOAD_NONE:
                    activity.tvStatus.setText("No files for download");
                    break;
            }
        };
    };

}


