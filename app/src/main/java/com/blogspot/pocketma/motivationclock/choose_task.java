package com.blogspot.pocketma.motivationclock;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import java.util.Random;

import com.firebase.ui.auth.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class choose_task extends AppCompatActivity {
    private static final String TAG = "Choose Task";
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_task);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // Write a message to the database

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference taskOneChecker = database.getReference("users/" + currentFirebaseUser.getUid() + "/task" + 1);

        DatabaseReference taskTwoChecker = database.getReference("users/" + currentFirebaseUser.getUid() + "/task" + 2);

        DatabaseReference taskThreeChecker = database.getReference("users/" + currentFirebaseUser.getUid() + "/task" + 3);

        DatabaseReference taskFourChecker = database.getReference("users/" + currentFirebaseUser.getUid() + "/task" + 4);

        DatabaseReference taskFiveChecker = database.getReference("users/" + currentFirebaseUser.getUid() + "/task" + 5);


        generateTask("task1",taskOneChecker, currentFirebaseUser.getUid(), database);
        generateTask("task2", taskTwoChecker, currentFirebaseUser.getUid(), database);
        generateTask("task3", taskThreeChecker, currentFirebaseUser.getUid(), database);
        generateTask("task4", taskFourChecker, currentFirebaseUser.getUid(), database);
        generateTask("task5", taskFiveChecker, currentFirebaseUser.getUid(), database);


    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }


    // When called, will open the Home Page
    public void openHome(View view) {
        Intent newIntent = new Intent(this, home.class);
        startActivity(newIntent);
    }

    // When called, will open the Alarm Page
    public void openAlarm(View view) {
        Intent newIntent = new Intent(this, alarm_change.class);
        startActivity(newIntent);
    }

    // When called, will open Choose Task Page
    public void openBored(View view) {
        Intent newIntent = new Intent(this, choose_task.class);
        startActivity(newIntent);
    }

    public void openTask(View view) {
        Intent newIntent = new Intent(this, ListTasks.class);
        startActivity(newIntent);
    }


    // When Called, will open the Achievements page
    public void openAchievements(View view) {
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
        Toast helpMessage = Toast.makeText(getApplicationContext(), "Choose daily tasks to complete!", Toast.LENGTH_LONG);
        helpMessage.setGravity(Gravity.CENTER, 0, 0);

        if (id == R.id.action_name) {
            helpMessage.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int randInt() {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will not get very good / not very random results.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt(5) + 1;

        return randomNum;
    }

    public void generateTask(final String taskNum, final DatabaseReference Checker, final String userID, final FirebaseDatabase database)
    {
        final ImageButton delete = new ImageButton(this);
        final ImageButton complete = new ImageButton(this);
        final Button task = new Button(this);

        delete.setImageResource(R.drawable.ic_cancel);
        complete.setImageResource(R.drawable.ic_checkmark);
        // Read from the database
        Checker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is used to write to the user's Tasks Location with the Task Title
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                if (value == null)
                {
                    int x = randInt();
                    String task = "";
                    switch (x){
                        case(1):
                            task = "Break" + randInt();
                            break;
                        case(2):
                            task = "Etiquette" + randInt();
                            break;
                        case(3):
                            task = "Fitness" + randInt();
                            break;
                        case(4):
                            task = "Food" + randInt();
                            break;
                        case(5):
                            task = "Meditate" + randInt();
                            break;
                    }

                    final DatabaseReference mReference = database.getReference(task);
                    writeTask(userID, taskNum, mReference.getKey());
                    return;
                }
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
                });
                // Generates the buttons
                int view = 0;
                switch (taskNum)
                {
                    case "task1":
                        view = R.id.horizontalTasks1;
                        break;
                    case "task2":
                        view = R.id.horizontalTasks2;
                        break;
                    case "task3":
                        view = R.id.horizontalTasks3;
                        break;
                    case "task4":
                        view = R.id.horizontalTasks4;
                        break;
                    case "task5":
                        view = R.id.horizontalTasks5;
                        break;
                }
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

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(choose_task.this, "Failed to read value",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void writeTask(String userId, String taskNumber, String Task) {

        mDatabase.child("users").child(userId).child(taskNumber).setValue(Task);
    }

}
