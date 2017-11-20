package com.blogspot.pocketma.motivationclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Random;

/**
 * Created by pillow's zenbook on 11/19/2017.
 */ //needs to be implemented

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*String state = intent.getExtras().getString("extra");
        Intent serviceIntent = new Intent(context,RingtonePlayingService.class);
        serviceIntent.putExtra("extra", state);

        context.startService(serviceIntent);*/

        Log.e("We are in the receiver.", "Yay!");

        //fetches extra string from the first_intent
        String get_your_string = intent.getExtras().getString("extra");
                Log.e("what is the key?", get_your_string);

        //create an intent for the rington service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);
        context.startService(service_intent);

        //pass the extra string from add_alarm to RingtonePlayingService.java
        service_intent.putExtra("extra", get_your_string);

        //start the ringtone service
        context.startService(service_intent);


    }



}
