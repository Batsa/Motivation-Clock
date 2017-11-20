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
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private AlarmReceiver alarm;

    add_alarm inst;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        this.context = this;

        //alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //set alarm to time that you picked
        final Calendar calendar = Calendar.getInstance();

        //initialize the time picker
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);

        //initialize the intent
        final Intent first_intent = new Intent(this.context, AlarmReceiver.class);

        //initialize the button to save the alarm
        Button save_alarm = (Button) findViewById(R.id.button8);//button 8 = save button

        //onclick listener for the save button
        save_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the calendar instance with the time from the time picker
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                //get the hour and minute from the time
                int hour = alarmTimePicker.getHour();
                int minute = alarmTimePicker.getMinute();
                //set the hour and minute to string values and convert to better format for reading time
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

               if (hour > 12)
                        hour_string = String.valueOf(hour - 12);
                if (minute < 10)
                        minute_string = "0" + String.valueOf(minute);


                Toast.makeText(getApplicationContext(),"Set an alarm for " + hour_string +
                        ":" + minute_string, Toast.LENGTH_SHORT).show();


                //put in extra string into my intent
                //tells clock that you pressed the alarm_on button
                first_intent.putExtra("extra", "alarm on");


                //creates pending intent until specified time
                pendingIntent = PendingIntent.getBroadcast(add_alarm.this, 0,
                        first_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                //above we need to change to keep repeating the sound bite
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);



//to cancel an alarm ->this code will actually need to go thru a pop up
                Button alarm_off = (Button) findViewById(R.id.button7);
                //this is temporarily set to the cancel button on the add_alarm page
                //we will need to set this button to the Turn off alarm button on the "NEW" pop up
                alarm_off.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //alarmManager.cancel(pendingIntent
                        Toast.makeText(context, "Turning off alarm", Toast.LENGTH_SHORT).show();

                        alarmManager.cancel(pendingIntent);
                        //visit part 6 of annaxu video 5:46 for if/else stop alarm
                        //put extra intent into first_intent
                        //tells clock that you pressed stop alarm
                        first_intent.putExtra("extra", "alarm off")
                        sendBroadcast(first_intent);
                    }
                });


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
