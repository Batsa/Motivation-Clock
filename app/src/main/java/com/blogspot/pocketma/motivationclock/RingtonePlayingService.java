package com.blogspot.pocketma.motivationclock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

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

public class RingtonePlayingService extends Service{

    MediaPlayer media_song;

    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("Local service", "Received start id " + startId + ": " + intent);

        //fetch the extra string values
        String state = intent.getExtras().getString("extra");
        //switch statement for alarm on or alarm off
        /*assert  state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }*/

        media_song  = MediaPlayer.create(this, R.raw.mcgregor1);
        media_song.start();


        return START_NOT_STICKY;
    }
}
