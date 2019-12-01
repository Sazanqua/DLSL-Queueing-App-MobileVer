package com.example.dlslqueueingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    public static int holder = 0;
    public static int counter = 0;


    @Override
    public void onReceive(Context context, Intent intent) {
        holder = 1;
        if(counter==0){
            Toast.makeText(context, "NOTIFICATION STOPPED", Toast.LENGTH_LONG).show();
            counter++;
        }
    }
}
