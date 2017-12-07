package com.blogspot.pocketma.motivationclock;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
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

        //Toast.makeText(this,"something should play", Toast.LENGTH_SHORT).show();
        //create an instance of the media player

        Bundle ringtonePlayerData = intent.getExtras();
        int ringtonePlayerNumber = ringtonePlayerData.getInt("alarmClipNumber");
        switch (ringtonePlayerNumber){
            case 1:
                motivatingQuote = MediaPlayer.create(this, R.raw.create_the_time_and_sich_to_do_stuff);
                motivatingQuote.start();
                break;
            case 2:
                motivatingQuote = MediaPlayer.create(this, R.raw.do_or_do_not_there_is_no_try);
                motivatingQuote.start();
                break;
            case 3:
                motivatingQuote = MediaPlayer.create(this, R.raw.dont_be_afraid_to_fail);
                motivatingQuote.start();
                break;
            case 4:
                motivatingQuote = MediaPlayer.create(this, R.raw.finding_nemo_just_keep_swimming);
                motivatingQuote.start();
                break;
            case 5:
                motivatingQuote = MediaPlayer.create(this, R.raw.focus_on_whats_right);
                motivatingQuote.start();
                break;
            case 6:
                motivatingQuote = MediaPlayer.create(this, R.raw.get_out_of_your_comfort_zone);
                motivatingQuote.start();
                break;
            case 7:
                motivatingQuote = MediaPlayer.create(this, R.raw.if_you_cant_tolerate_thats_where_u_change);
                motivatingQuote.start();
                break;
            case 8:
                motivatingQuote = MediaPlayer.create(this, R.raw.inches_for_the_win);
                motivatingQuote.start();
                break;
            case 9:
                motivatingQuote = MediaPlayer.create(this, R.raw.invest_in_yourself);
                motivatingQuote.start();
                break;
            case 10:
                motivatingQuote = MediaPlayer.create(this, R.raw.its_about_how_much_you_can_take);
                motivatingQuote.start();
                break;
            case 11:
                motivatingQuote = MediaPlayer.create(this, R.raw.judge_me_by_my_size);
                motivatingQuote.start();
                break;
            case 12:
                motivatingQuote = MediaPlayer.create(this, R.raw.keep_going_and_never_go_back);
                motivatingQuote.start();
                break;
            case 13:
                motivatingQuote = MediaPlayer.create(this, R.raw.keep_pushing_and_dont_go_back);
                motivatingQuote.start();
                break;
            case 14:
                motivatingQuote = MediaPlayer.create(this, R.raw.lion_king_the_past_can_hurt);
                motivatingQuote.start();
                break;
            case 15:
                motivatingQuote = MediaPlayer.create(this, R.raw.listen_and_follow_your_heart);
                motivatingQuote.start();
                break;
            case 16:
                motivatingQuote = MediaPlayer.create(this, R.raw.master_oogways_awesome_saying);
                motivatingQuote.start();
                break;
            case 17:
                motivatingQuote = MediaPlayer.create(this, R.raw.mistakes_make_a_person);
                motivatingQuote.start();
                break;
            case 18:
                motivatingQuote = MediaPlayer.create(this, R.raw.pain_goes_to_the_next_level);
                motivatingQuote.start();
                break;
            case 19:
                motivatingQuote = MediaPlayer.create(this, R.raw.see_the_belief_within_yourself);
                motivatingQuote.start();
                break;
            case 20:
                motivatingQuote = MediaPlayer.create(this, R.raw.think_big_but_start_small);
                motivatingQuote.start();
                break;
        }

        return START_NOT_STICKY;
    }
}
