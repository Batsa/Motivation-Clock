package com.blogspot.pocketma.motivationclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Random;

import org.w3c.dom.Text;

public class add_alarm extends AppCompatActivity {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    TimePicker alarmTimePicker;
    AlarmReceiver alarm;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        this.context = this;

        //creating the calendar instance that we will use to get the day and time
        final Calendar calendar = Calendar.getInstance();

        //get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);

        //creates intent for the alarm to fire off
        final Intent myIntent = new Intent(this.context, AlarmReceiver.class);

        Button startAlarm = (Button) findViewById(R.id.button8);
        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.SECOND, 3);

                //sets the calendar date and hour to the where the time picker is
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                //pending intent to hold off the intent until the specified time
                pendingIntent = PendingIntent.getBroadcast(add_alarm.this, 0, myIntent, pendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                //message to let you know that you set the alarm
                int hour = alarmTimePicker.getHour();
                int minute = alarmTimePicker.getMinute();
                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);
                if (hour>12)
                    hourString = String.valueOf(hour- 12);
                if(minute < 10)
                    minuteString = "0" + String.valueOf(minute);

                Toast.makeText(getApplicationContext(), "Alarm set for " + hourString +":" + minuteString,
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button stopAlarm = (Button) findViewById(R.id.button7);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alarmManager.cancel(pendingIntent);
                Toast.makeText(getApplicationContext(), "Alarm Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }//end of oncreate

    // When called, will open the Home Page
    public void openHome(View view)
    {
        Intent newIntent = new Intent(this, home.class);
        startActivity(newIntent);
    }

    // When called, will open the Alarm Page
    public void openAlarm(View view)
    {
        Intent newIntent = new Intent(this, alarm_change.class);
        startActivity(newIntent);
    }

    // When called, will open Choose Task Page
    public void openBored(View view)
    {
        Intent newIntent = new Intent(this, choose_task.class);
        startActivity(newIntent);
    }

    // When Called, will open the Achievements page
    public void openAchievements(View view)
    {
        Intent newIntent = new Intent(this, achievements.class);
        startActivity(newIntent);
    }

    // Creates the help button on the top right corner of the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Displays pop up bubble when the help button is pressed. Change the string to change message
    // Change Toast.LENGTH_LONG to Toast.LENGTH_SHORT to lower duration of bubble on screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Toast helpMessage = Toast.makeText(getApplicationContext(),"Choose the settings for you alarms!",Toast.LENGTH_LONG);
        helpMessage.setGravity(Gravity.CENTER, 0, 0);

        if (id == R.id.action_name) {
            helpMessage.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
