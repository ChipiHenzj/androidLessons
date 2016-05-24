package com.example.tetianapriadko.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Lesson88 extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    MyTask myTask;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson88);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
    }

    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                myTask = new MyTask();
                myTask.execute();
                break;
            case R.id.btnGet:
                showResult();
                break;
            default:
                break;
        }
    }

    private void showResult() {
        if (myTask == null) {
            return;
        }

        int result = -1;
        try {
            Log.d(LOG_TAG, "Try to get result");
            Toast.makeText(this, "Try to get result", Toast.LENGTH_SHORT).show();

            result = myTask.get();

            Log.d(LOG_TAG, "get returns " + result);
            Toast.makeText(this, "get returns " + result, Toast.LENGTH_SHORT).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100500;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            tvInfo.setText("End. Result = " + result);
            Log.d(LOG_TAG, "End. Result = " + result);
        }
    }


}