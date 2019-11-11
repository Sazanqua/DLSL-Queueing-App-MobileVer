package com.example.dlslqueueingapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;


import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String NotifID = "Queue ID";
    public static final String notifName = "Queue Notification";

    public NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            createChannel();
        }

    }
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel(){
        NotificationChannel channel = new NotificationChannel(NotifID, notifName, NotificationManager.IMPORTANCE_HIGH);

        channel.enableLights(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.canBypassDnd();
        channel.shouldShowLights();
        channel.enableVibration(true);
        getManeger().createNotificationChannel(channel);
    }

    public NotificationManager getManeger(){
        if(mManager==null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannerlNotification(String title, String msg){
        return new NotificationCompat.Builder(getApplicationContext(), NotifID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.delasallelipaa);

    }
}
