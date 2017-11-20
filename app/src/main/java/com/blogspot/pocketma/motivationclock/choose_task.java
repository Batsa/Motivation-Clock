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
        final Button taskButton = new Button(this);

        delete.setImageResource(R.drawable.ic_cancel);
        complete.setImageResource(R.drawable.ic_checkmark);
        // Read from the database
        Checker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is used to write to the user's Tasks Location with the Task Title
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                int x = randInt();
                final String finalTask = value;
                if (value == null)
                {
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
                            task = "PushUps" + randInt();
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
                        taskButton.setText(value2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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

                hl.addView(taskButton, inputParameters);
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
                            layout.removeView(taskButton);
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
                            layout.removeView(taskButton);
                        }
                        switch (finalTask)
                        {
                            case("Break1"):
                            case("Break2"):
                            case("Break3"):
                            case("Break4"):
                            case("Break5"):
                                DatabaseReference achievementsNameReference1 = database.getReference("users/" + userID + "/Achievements/Let the Stress Flow Away/Progress");
                                DatabaseReference achievementsNameReference2 = database.getReference("users/" + userID + "/Achievements/Remember: Just Relax/Progress");
                                DatabaseReference achievementsNameReference3 = database.getReference("users/" + userID + "/Achievements/Start the Good Vibes Man/Progress");
                                achievementsNameReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Let the Stress Flow Away").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                achievementsNameReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Remember: Just Relax").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                achievementsNameReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Start the Good Vibes Man").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                break;
                            case("Etiquette1"):
                            case("Etiquette2"):
                            case("Etiquette3"):
                            case("Etiquette4"):
                            case("Etiquette5"):
                                DatabaseReference EtiquetteNameReference1 = database.getReference("users/" + userID + "/Achievements/M'Ladies and M'Dudes/Progress");
                                DatabaseReference EtiquetteNameReference2 = database.getReference("users/" + userID + "/Achievements/RESPECT/Progress");
                                DatabaseReference EtiquetteNameReference3 = database.getReference("users/" + userID + "/Achievements/Spread Some Luhv/Progress");
                                EtiquetteNameReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("M'Ladies and M'Dudes").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                EtiquetteNameReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("RESPECT").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                EtiquetteNameReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Spread Some Luhv").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                break;

                            case("Fitness1"):
                            case("Fitness2"):
                            case("Fitness3"):
                            case("Fitness4"):
                            case("Fitness5"):
                                DatabaseReference fitnessNameReference1 = database.getReference("users/" + userID + "/Achievements/Running on Time/Progress");
                                // DatabaseReference fitnessNameReference2 = database.getReference("users/" + userID + "/Achievements/Sparks from Behind/Progress");
                                DatabaseReference fitnessNameReference3 = database.getReference("users/" + userID + "/Achievements/Endurance Machine/Progress");
                                fitnessNameReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Running on Time").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                               /* fitnessNameReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Sparks from Behind").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                }); */
                                fitnessNameReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Endurance Machine").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                break;
                            case("PushUps1"):
                            case("PushUps2"):
                            case("PushUps3"):
                            case("PushUps4"):
                            case("PushUps5"):
                                DatabaseReference pushUpsNameReference1 = database.getReference("users/" + userID + "/Achievements/Up & Down/Progress");
                                DatabaseReference pushUpsNameReference2 = database.getReference("users/" + userID + "/Achievements/Biological Piston/Progress");
                                DatabaseReference pushUpsNameReference3 = database.getReference("users/" + userID + "/Achievements/Ye Who Shifted Terra/Progress");
                                pushUpsNameReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Up & Down").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                pushUpsNameReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Biological Piston").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                pushUpsNameReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Ye Who Shifted Terra").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                break;
                            case("Meditate1"):
                            case("Meditate2"):
                            case("Meditate3"):
                            case("Meditate4"):
                            case("Meditate5"):
                                DatabaseReference meditateNameReference1 = database.getReference("users/" + userID + "/Achievements/Iris Passing Intensifies/Progress");
                                DatabaseReference meditateNameReference2 = database.getReference("users/" + userID + "/Achievements/Inside Road to Nirvana/Progress");
                                DatabaseReference meditateNameReference3 = database.getReference("users/" + userID + "/Achievements/Floating to Stage 10/Progress");
                                meditateNameReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Iris Passing Intensifies").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                meditateNameReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Inside Road to Nirvana").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                meditateNameReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String x = dataSnapshot.getValue(String.class);
                                        int y = Integer.parseInt(x);
                                        y++;
                                        mDatabase.child("users").child(userID).child("Achievements").child("Floating to Stage 10").child("Progress").setValue(Integer.toString(y));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                break;
                        }



                        Checker.removeValue();

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void writeTask(String userId, String taskNumber, String Task) {

        mDatabase.child("users").child(userId).child(taskNumber).setValue(Task);
    }

}
