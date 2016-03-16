package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Lesson72_1 extends AppCompatActivity {

    TextView tvInfo;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson72);

        tvInfo = (TextView)findViewById(R.id.tvInfo);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    protected void onResume() {
        String listValue = sp.getString("list", "not chosen");
        tvInfo.setText("List value  - " + listValue);
        super.onResume();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0, 1, 0, "Preferences");
        mi.setIntent(new Intent(this, Lesson72_2.class));
        return super.onCreateOptionsMenu(menu);
    }
}

