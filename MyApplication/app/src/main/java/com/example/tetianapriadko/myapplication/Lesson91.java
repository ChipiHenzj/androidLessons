package com.example.tetianapriadko.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Lesson91 extends AppCompatActivity {

    MyTask myTask;
    TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson91);
        Log.d("myLogs", "create MainActivity: " + this.hashCode());

        textView = (TextView) findViewById(R.id.tv);

        myTask = (MyTask) getLastCustomNonConfigurationInstance();
        if (myTask == null) {
            myTask = new MyTask();
            myTask.execute();
        }
        // передаем в MyTask ссылку на текущее MainActivity
        myTask.link(this);

        Log.d("myLogs", "create MyTask: " + myTask.hashCode());
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        // удаляем из MyTask ссылку на старое MainActivity
        myTask.unLink();
        return myTask;
    }

    static class MyTask extends AsyncTask<String, Integer, Void> {

        Lesson91 activity;

        // получаем ссылку на MainActivity
        void link(Lesson91 act) {
            activity = act;
        }

        // обнуляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (int i = 1; i <= 10; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d("myLogs", "i = " + i + ", MyTask: " + this.hashCode()
                            + ", MainActivity: " + activity.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.textView.setText("i = " + values[0]);
        }
    }
}