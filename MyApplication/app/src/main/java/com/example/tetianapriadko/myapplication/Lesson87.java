package com.example.tetianapriadko.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Lesson87 extends AppCompatActivity {

    MyTask myTask;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson87);

        tvInfo = (TextView)findViewById(R.id.tvInfo);
    }

    public void onclick(View v) {
        myTask = new MyTask();
        myTask.execute("file_1", "file_2", "file_3", "file_4");
    }


    class MyTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText("Begin");
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                int cnt = 0;
                for (String s : params) {
                    // загружаем файл
                    downloadFile(s);
                    // выводим промежуточные результаты
                    publishProgress(++cnt);
                }
                // разъединяемся
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvInfo.setText("Downloaded " + values[0] + " files");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tvInfo.setText("End");
        }

        private void downloadFile(String s) throws InterruptedException {
            TimeUnit.SECONDS.sleep(2);
        }

    }
}
