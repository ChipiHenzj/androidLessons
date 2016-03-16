package com.example.tetianapriadko.myapplication;


import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Lesson71_2 extends PreferenceActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref71);

        }
}
