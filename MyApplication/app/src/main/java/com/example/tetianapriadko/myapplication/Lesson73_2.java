package com.example.tetianapriadko.myapplication;


import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

public class Lesson73_2 extends PreferenceActivity {

    CheckBoxPreference chb3;
    PreferenceCategory categ2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref73);

        chb3 = (CheckBoxPreference) findPreference("chb3");
        categ2 = (PreferenceCategory) findPreference("categ2");

        categ2.setEnabled(chb3.isChecked());

        chb3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                categ2.setEnabled(chb3.isChecked());
                return false;
            }
        });
    }
}
