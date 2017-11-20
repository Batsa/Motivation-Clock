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
import android.widget.Toast;

import java.util.Random;

public class RingtonePlayingService extends Service{

    MediaPlayer motivatingQuote;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){

        Toast.makeText(this,"something should play", Toast.LENGTH_SHORT).show();
        //create an instance of the media player
        motivatingQuote = MediaPlayer.create(this, R.raw.do_or_do_not_there_is_no_try);
        motivatingQuote.start();
        return START_NOT_STICKY;
    }
}
