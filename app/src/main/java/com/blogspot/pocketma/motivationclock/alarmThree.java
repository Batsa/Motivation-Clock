package com.blogspot.pocketma.motivationclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Context;
import android.widget.Button;
import android.app.AlertDialog;
import java.util.Calendar;
import java.util.Date;

public class alarmThree extends AppCompatActivity {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    PendingIntent sundayPendingIntent;
    PendingIntent mondayPendingIntent;
    PendingIntent tuesdayPendingIntent;
    PendingIntent wednesdayPendingIntent;
    PendingIntent thursdayPendingIntent;
    PendingIntent fridayPendingIntent;
    PendingIntent saturdayPendingIntent;
    TimePicker alarmTimePicker;
    AlarmReceiver alarm;
    Context context;
    //creating the calendar instance that we will use to get the day and time
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_three);
        this.context = this;

        //get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void startAlarmOnClick(View view){
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        //creates intent for the alarm to fire off
        final Intent alarmSetIntent = new Intent(this.context, AlarmReceiver.class);

        //intent to change the box text
        final Intent alarmTextIntent = new Intent (this.context, alarm_change.class);

        //checkboxes to see what days are checked
        CheckBox isSundayChecked = (CheckBox) findViewById(R.id.sundayBox);
        CheckBox isMondayChecked = (CheckBox) findViewById(R.id.mondayBox);
        CheckBox isTuesdayChecked = (CheckBox) findViewById(R.id.tuesdayBox);
        CheckBox isWednesdayChecked = (CheckBox) findViewById(R.id.wednesdayBox);
        CheckBox isThursdayChecked = (CheckBox) findViewById(R.id.thursdayBox);
        CheckBox isFridayChecked = (CheckBox) findViewById(R.id.fridayBox);
        CheckBox isSaturdayChecked = (CheckBox) findViewById(R.id.saturdayBox);


        //sets the calendar date and hour to the where the time picker is
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);
        //pending intent to hold off the intent until the specified time

        int clipNumber = getSoundClip();
        alarmSetIntent.putExtra("alarmClipNumber", clipNumber);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, pendingIntent.FLAG_UPDATE_CURRENT);
        Date currentTime = Calendar.getInstance().getTime();

        if (calendar.getTime().compareTo(currentTime) == -1){
            calendar.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getHour() + 24);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        if(isSundayChecked.isChecked()){
            //Toast.makeText(getApplicationContext(), "Sunday is Checked", Toast.LENGTH_SHORT).show();
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY );
            pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, sundayPendingIntent.FLAG_IMMUTABLE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
        }
        if(isMondayChecked.isChecked()){
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, mondayPendingIntent.FLAG_IMMUTABLE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
        }
        if(isTuesdayChecked.isChecked()){
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, tuesdayPendingIntent.FLAG_IMMUTABLE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
        }
        if(isWednesdayChecked.isChecked()){
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, wednesdayPendingIntent.FLAG_IMMUTABLE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
        }
        if(isThursdayChecked.isChecked()){
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, thursdayPendingIntent.FLAG_IMMUTABLE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
        }
        if(isFridayChecked.isChecked()){
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, fridayPendingIntent.FLAG_IMMUTABLE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
        }
        if(isSaturdayChecked.isChecked()){
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, saturdayPendingIntent.FLAG_IMMUTABLE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
        }

        //message to let you know that you set the alarm
        int hour = alarmTimePicker.getHour();
        int minute = alarmTimePicker.getMinute();
        String amOrPm;
        String hourString = String.valueOf(hour);
        String minuteString = String.valueOf(minute);
        //used to convert 24 hour time to 12 hour time
        if (hour>=12) {
            amOrPm = "PM";
            if(hour>12)
                hourString = String.valueOf(hour - 12);
        } else{
            amOrPm = "AM";
            if (hour == 0)
                hourString = String.valueOf(hour + 12);
        }
        if(minute < 10)
            minuteString = "0" + String.valueOf(minute);
        String alarmSetTo = "Alarm set for " + hourString + ":" + minuteString + " " + amOrPm;
        alarmTextIntent.putExtra("alarmThreeTime", alarmSetTo);          //used to switch the text of the button
        Toast.makeText(getApplicationContext(), "Alarm set for " + hourString +":" + minuteString,
                Toast.LENGTH_SHORT).show();
        startActivity(alarmTextIntent);                                 //go back to alarm screen
    }//end of start alarm button

    public void stopAlarmOnClick(View view){

        final Intent alarmSetIntent = new Intent(this.context, AlarmReceiver.class);

        //intent to change the text of the alarm button
        final Intent alarmTextIntent = new Intent(this.context, alarm_change.class);

        //changes the alarm three button text to alarm is off
        alarmTextIntent.putExtra("alarmThreeTime", "Alarm is off");
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, pendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, sundayPendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, mondayPendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, tuesdayPendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, wednesdayPendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, thursdayPendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, fridayPendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent = PendingIntent.getBroadcast(alarmThree.this, 0, alarmSetIntent, saturdayPendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);


        Toast.makeText(getApplicationContext(), "Alarm Cancelled",
                Toast.LENGTH_SHORT).show();
        //returns you to the alarm manager screen
        startActivity(alarmTextIntent);
    } //end of turn off alarm button


    //returns a number representing the clip number that is clicked on the alarm page. Default to the first button if nothing is pushed
    public int getSoundClip(){
        int clipNumber = 1;
        RadioButton button1 = (RadioButton) findViewById(R.id.createTheTime);
        RadioButton button2 = (RadioButton) findViewById(R.id.doOrDoNot);
        RadioButton button3 = (RadioButton) findViewById(R.id.dontBeAfraid);
        RadioButton button4 = (RadioButton) findViewById(R.id.justKeepSwimming);
        RadioButton button5 = (RadioButton) findViewById(R.id.focusOnWhatsRight);
        RadioButton button6 = (RadioButton) findViewById(R.id.getOut);
        RadioButton button7 = (RadioButton) findViewById(R.id.ifYouCantTolerate);
        RadioButton button8 = (RadioButton) findViewById(R.id.inchesForTheWin);
        RadioButton button9 = (RadioButton) findViewById(R.id.investInYourself);
        RadioButton button10 = (RadioButton) findViewById(R.id.itsAboutHowMuch);
        RadioButton button11 = (RadioButton) findViewById(R.id.judgeMe);
        RadioButton button12 = (RadioButton) findViewById(R.id.keepGoing);
        RadioButton button13 = (RadioButton) findViewById(R.id.keepPushing);
        RadioButton button14 = (RadioButton) findViewById(R.id.thePastCanHurt);
        RadioButton button15 = (RadioButton) findViewById(R.id.listenAndFollow);
        RadioButton button16 = (RadioButton) findViewById(R.id.masterOogway);
        RadioButton button17 = (RadioButton) findViewById(R.id.mistakesMake);
        RadioButton button18 = (RadioButton) findViewById(R.id.painGoesTo);
        RadioButton button19 = (RadioButton) findViewById(R.id.seeTheBelief);
        RadioButton button20 = (RadioButton) findViewById(R.id.thinkBig);

        if (button1.isChecked())
            clipNumber = 1;
        else if (button2.isChecked())
            clipNumber = 2;
        else if (button3.isChecked())
            clipNumber = 3;
        else if (button4.isChecked())
            clipNumber = 4;
        else if (button5.isChecked())
            clipNumber = 5;
        else if (button6.isChecked())
            clipNumber = 6;
        else if (button7.isChecked())
            clipNumber = 7;
        else if (button8.isChecked())
            clipNumber = 8;
        else if (button9.isChecked())
            clipNumber = 9;
        else if (button10.isChecked())
            clipNumber = 10;
        else if (button11.isChecked())
            clipNumber = 11;
        else if (button12.isChecked())
            clipNumber = 12;
        else if (button13.isChecked())
            clipNumber = 13;
        else if (button14.isChecked())
            clipNumber = 14;
        else if (button15.isChecked())
            clipNumber = 15;
        else if (button16.isChecked())
            clipNumber = 16;
        else if (button17.isChecked())
            clipNumber = 17;
        else if (button18.isChecked())
            clipNumber = 18;
        else if (button19.isChecked())
            clipNumber = 19;
        else if (button20.isChecked())
            clipNumber = 20;
        return clipNumber;
    }

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
        //Toast helpMessage = Toast.makeText(getApplicationContext(),"Choose the settings for you alarms!",Toast.LENGTH_LONG);
        //helpMessage.setGravity(Gravity.CENTER, 0, 0);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Choose the settings for you alarms!");
        AlertDialog alertDialog = alertDialogBuilder.create();

        if (id == R.id.action_name) {
            //helpMessage.show();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

