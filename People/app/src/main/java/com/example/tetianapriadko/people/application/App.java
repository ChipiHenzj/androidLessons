package com.example.tetianapriadko.people.application;

import android.app.Application;

import com.backendless.Backendless;
import com.example.tetianapriadko.people.constants.BACK_SETTINGS;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initBackEndless();
    }

    private void initBackEndless() {
        Backendless.initApp(this, BACK_SETTINGS.APP_ID, BACK_SETTINGS.SECRET_KEY, BACK_SETTINGS.VERSION);
    }
}
