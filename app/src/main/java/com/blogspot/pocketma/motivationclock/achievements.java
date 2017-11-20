package com.blogspot.pocketma.motivationclock;

import android.content.Context;
import android.content.Intent;
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
        for (int i = 1; i <= 19; i++) {
            DatabaseReference achievementReference = database.getReference("Achievements/Achievement" + i + "/Name");
            DatabaseReference achievementCompletion = database.getReference("Achievements/Achievement" + i + "/Completion");
            generateTask(achievementReference, achievementCompletion, currentFirebaseUser.getUid(), database);
        }


    }

    public void generateTask(final DatabaseReference Name, final DatabaseReference Completion, final String userID, final FirebaseDatabase database)
    {
        final Button achievementButton = new Button(this);


        // Read from the database
        Name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is used to write to the user's Tasks Location with the Task Title
                final String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                DatabaseReference userProgress = database.getReference("users/" + userID + "/Achievements/" + value + "/Progress");
                final DatabaseReference completionRequirement = database.getReference("users/" + userID + "/Achievements/" + value + "/Completion");

                // To get the amount needed to complete the task and store it in User's Completion Requirement
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
                final LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                String x = value;
                achievementButton.setText(x);



                userProgress.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String x = dataSnapshot.getValue(String.class);
                        if (x == null) {
                            mDatabase.child("users").child(userID).child("Achievements").child(value).child("Progress").setValue("0");
                            x = "0";
                        }

                        final int progress = Integer.parseInt(x);
                        completionRequirement.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String y = dataSnapshot.getValue(String.class);
                                final int completion = Integer.parseInt(y);
                                if (progress >= completion)
                                {
                                    LinearLayout listOfCompleted = (LinearLayout) findViewById(R.id.completed);
                                    listOfCompleted.addView(achievementButton,parameters);
                                }
                                else
                                {
                                    LinearLayout listOfCompleted = (LinearLayout) findViewById(R.id.notCompleted);
                                    listOfCompleted.addView(achievementButton,parameters);
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
                /*
                // Is used to get the String from the Task Title and set it to text of task
                DatabaseReference newTaskReference = database.getReference(value);
                newTaskReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value2 = dataSnapshot.getValue(String.class);
                        task.setText(value2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(choose_task.this, "Failed to read value Inner loop",
                                Toast.LENGTH_SHORT).show();
                    }
                }); */
                // Generates the buttons
/*
                LinearLayout hl = (LinearLayout) findViewById(view);
                LinearLayout.LayoutParams inputParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                LinearLayout.LayoutParams deleteParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.0f);
                LinearLayout.LayoutParams completeParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2.0f);

                hl.addView(task, inputParameters);
                hl.addView(complete, completeParameters);
                hl.addView(delete, deleteParameters);
                delete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ViewGroup layout = (ViewGroup) delete.getParent();
                        if (null != layout) //for safety only  as you are doing onClick
                        {
                            layout.removeView(delete);
                            layout.removeView(complete);
                            layout.removeView(task);
                        }
                        Checker.removeValue();
                    }
                });
                complete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ViewGroup layout = (ViewGroup) complete.getParent();
                        if (null != layout) //for safety only  as you are doing onClick
                        {
                            layout.removeView(delete);
                            layout.removeView(complete);
                            layout.removeView(task);
                        }
                        Toast.makeText(choose_task.this, "Updating The Achievements Progress",
                                Toast.LENGTH_SHORT).show();
                        Checker.removeValue();

                    }
                });

            }
*/
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(achievements.this, "Failed to read value",
                        Toast.LENGTH_SHORT).show();
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
        //CHANGE: from toast to dialog box (TC)
        //Toast helpMessage = Toast.makeText(getApplicationContext(),"Complete daily tasks and get achievements!",Toast.LENGTH_LONG);
        //helpMessage.setGravity(Gravity.CENTER, 0, 0);
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
