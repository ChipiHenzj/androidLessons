package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Lesson71_1 extends AppCompatActivity {

    TextView tvInfo;
    
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson71);

        tvInfo = (TextView) findViewById(R.id.tvInfo);

        // получаем SharedPreferences, которое работает с файлом настроек
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        // полная очистка настроек
        // sp.edit().clear().commit();
    }

    protected void onResume() {
        Boolean notif = sp.getBoolean("notif", false);
        String address = sp.getString("address", "");

        String text = "Notifications are "
                + ((notif) ? "enabled, address = " + address : "disabled");
        tvInfo.setText(text);

        super.onResume();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuNotif = menu.add(0, 1, 0, "Preferences");
        menuNotif.setIntent(new Intent(this, Lesson71_2.class));
        return super.onCreateOptionsMenu(menu);
    }
}
