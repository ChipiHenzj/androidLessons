package com.example.tetianapriadko.myapplication;


import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;



public class Lesson99_2 extends Service {

    NotificationManager nm;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendNotif();
        return super.onStartCommand(intent, flags, startId);
    }


    void sendNotif() {

        Intent intent = new Intent(this, Lesson99_1.class);
        intent.putExtra(Lesson99_1.FILE_NAME, "somefile");

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder notif = new Notification.Builder(this);
        notif.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification's title")
                .setContentText("Notification's text")
                .setContentIntent(pIntent)
                .setContentInfo("Info")

                .build();

        nm.notify(1, notif.getNotification());
        startForeground (1, notif.getNotification());
    }
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
