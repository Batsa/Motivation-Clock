package com.blogspot.pocketma.motivationclock;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.LinkAddress;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class achievements extends AppCompatActivity {
    private static final String TAG = "Choose Task";
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // Write a message to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for (int i = 1; i <= 23; i++) {
            // Gets the name of the achievement, and the number needed to complete it.
            // The name will be needed to create the button.
            // The # needed to complete will be used to update the user's achievement directory completion #, and be compared to
            // to determine which section will the achievement be put into.
            // To get the description later, will need to do something along the same lines as the below, and pass it in also.
            // Also, I may want to use the popup box that tina had
            DatabaseReference achievementReference = database.getReference("Achievements/Achievement" + i + "/Name");
            DatabaseReference achievementCompletion = database.getReference("Achievements/Achievement" + i + "/Completion");
            DatabaseReference achievementDescription = database.getReference("Achievements/Achievement" + i + "/Description");
            // Pass in the name of the achievement, the number needed to complete it, the id of the user, and the database reference
            generateTask(achievementReference, achievementCompletion, achievementDescription, currentFirebaseUser.getUid(), database);
        }


    }

    public void generateTask(final DatabaseReference Name, final DatabaseReference Completion, final DatabaseReference Description, final String userID, final FirebaseDatabase database)
    {
        // Will be used to output to the screen the name of the achievement
        final ImageButton achievementButton = new ImageButton(this);
        achievementButton.setImageAlpha(400);
        // Clicking on the Achievement will open the Description of the achievement
        achievementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Description.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String value = dataSnapshot.getValue(String.class);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(achievements.this);
                        alertDialogBuilder.setMessage(value);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        // Read from the database
        Name.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Gets the name of the achievement
                final String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                final LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                parameters.setMargins(50,10,10,1);
                // String x = value;
//                achievementButton.setText(x);

                // References to the user's own Progress and the completion Requirement for the achievement.
                // Is the completion requirement necessary I wonder? We already have the completion number that was passed in
                final DatabaseReference userProgress = database.getReference("users/" + userID + "/Achievements/" + value + "/Progress");
                final DatabaseReference completionRequirement = database.getReference("users/" + userID + "/Achievements/" + value + "/Completion");

                // To get the amount needed to complete the task and store it in User's Completion Requirement for that achievement
                // We only need a listener for a single event because the completion requirement should never change/update
                Completion.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String value2 = dataSnapshot.getValue(String.class);
                        mDatabase.child("users").child(userID).child("Achievements").child(value).child("Completion").setValue(value2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // Is used to check the User's progress.
                // And then build the button into the corresponding layout
                userProgress.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String x = dataSnapshot.getValue(String.class);
                        // Initializes the user's progress to 0 if it is the first time for the user (No previous data for progress)
                        if (x == null) {
                            mDatabase.child("users").child(userID).child("Achievements").child(value).child("Progress").setValue("0");
                            x = "0";
                        }


                        // achievementButton.setBackgroundColor(Color.TRANSPARENT);
                        // Will be used to compare with completion later on to determine where to put the achievement
                        final int progress = Integer.parseInt(x);

                        // Gets the completion requirement from the user, and build the button depending on whether the user
                        // completed the achievement or not
                        completionRequirement.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String y = dataSnapshot.getValue(String.class);
                                final int completion = Integer.parseInt(y);
                                int progressParameter = 0;
                                if (progress > completion)
                                    progressParameter = completion;
                                else
                                    progressParameter = progress;
                                String words = value.replaceAll("[^a-zA-Z ]", "").toLowerCase();
                                words = words.replaceAll("\\s+","_");
                                int resId = getResources().getIdentifier(words + "_" + progressParameter + "_" + completion, "drawable", getPackageName());
                                achievementButton.setBackgroundResource(resId);
                                if (progress >= completion)
                                {
                                    LinearLayout listOfCompleted = (LinearLayout) findViewById(R.id.completed);
                                    listOfCompleted.addView(achievementButton,parameters);
                                }
                                else
                                {
                                    LinearLayout listOfNotCompleted = (LinearLayout) findViewById(R.id.notCompleted);
                                    listOfNotCompleted.addView(achievementButton,parameters);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    private void writeTask(String userId, String taskNumber, String Task) {

        mDatabase.child("users").child(userId).child(taskNumber).setValue(Task);
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Complete daily tasks and get achievements!");
        AlertDialog alertDialog = alertDialogBuilder.create();


        if (id == R.id.action_name) {
            //helpMessage.show();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
