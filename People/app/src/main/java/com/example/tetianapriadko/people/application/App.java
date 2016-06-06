package com.example.tetianapriadko.people.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.backendless.Backendless;
import com.example.tetianapriadko.people.constants.BACK_SETTINGS;

public class App extends Application {

    private static Bitmap croppedBitmap;

    @Override
    public void onCreate() {
        super.onCreate();
        initBackEndless();
    }

    private void initBackEndless() {
        Backendless.initApp(this, BACK_SETTINGS.APP_ID, BACK_SETTINGS.SECRET_KEY, BACK_SETTINGS.VERSION);
    }

    public static Bitmap getCroppedBitmap() {
        return croppedBitmap;
    }

    public static void setCroppedBitmap(Bitmap bitmap) {
        croppedBitmap = bitmap;
    }

    public static void clearCroppedBitmap() {
        croppedBitmap.recycle();
        croppedBitmap = null;
    }
}
