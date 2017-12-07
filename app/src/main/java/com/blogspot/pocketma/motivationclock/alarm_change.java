package com.blogspot.pocketma.motivationclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.app.AlertDialog; //uses v7
import android.content.DialogInterface;


public class alarm_change extends AppCompatActivity {
    alarm_change instance;
    Bundle alarmTimeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_change);
        alarmTimeData = getIntent().getExtras();
        if (alarmTimeData == null)
            return;
        String alarmTimeString = alarmTimeData.getString("alarmOneTime");
        Button alarmOneButton = findViewById(R.id.alarmOneButton);
        alarmOneButton.setText(alarmTimeString);
    }


    //opens the first alarm clock page
    public void openAlarmOne(View view){
        Intent alarmOneIntent = new Intent(this, alarmOne.class);
        startActivity(alarmOneIntent);
    }

    public void openAlarmTwo(View view){
        Intent alarmTwoIntent = new Intent(this, alarmTwo.class);
        startActivity(alarmTwoIntent);
    }

    public void openAlarmThree(View view){
        Intent alarmThreeIntent = new Intent(this, alarmThree.class);
        startActivity(alarmThreeIntent);
    }

    public void openAlarmFour(View view){
        Intent alarmFourIntent = new Intent(this, alarmFour.class);
        startActivity(alarmFourIntent);
    }

    public void openAlarmFive(View view){
        Intent alarmFiveIntent = new Intent(this, alarmFive.class);
        startActivity(alarmFiveIntent);
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
        //CHANGE: from toast to dialog box (TC)
        //Toast helpMessage = Toast.makeText(getApplicationContext(),"Your Motivational Alarms!",Toast.LENGTH_LONG);
        //helpMessage.setGravity(Gravity.CENTER, 0, 0);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your Motivational Alarms!");
        AlertDialog alertDialog = alertDialogBuilder.create();


        if (id == R.id.action_name) {
            //helpMessage.show();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
