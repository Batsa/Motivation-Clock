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
import android.widget.Toast;

/**
 * Created by pillow's zenbook on 11/19/2017.
 */ //needs to be implemented

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Alarm is received", Toast.LENGTH_SHORT).show();
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);
        context.startService(serviceIntent);

    }

}
