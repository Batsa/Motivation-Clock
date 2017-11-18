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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

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



       // Write a message to the database
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference taskOneReference = database.getReference("Meditate" + randInt());
       DatabaseReference taskTwoReference = database.getReference("Meditate" + randInt());
       DatabaseReference taskThreeReference = database.getReference("Meditate" + randInt());
       DatabaseReference taskFourReference = database.getReference("Meditate" + randInt());
       DatabaseReference taskFiveReference = database.getReference("Meditate" + randInt());

       final Button task1 = new Button(this);
       final Button task2 = new Button(this);
       final Button task3 = new Button(this);
       final Button task4 = new Button(this);
       final Button task5 = new Button(this);

       // Read from the database
       taskOneReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot dataSnapshot) {
                                           // This method is called once with the initial value and again
                                           // whenever data at this location is updated.
                                           String value = dataSnapshot.getValue(String.class);
                                           Log.d(TAG, "Value is: " + value);

                                           task1.setText(value);
                                           LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutTasks);
                                           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                           ll.addView(task1, lp);
                                       }

                                       @Override
                                       public void onCancelled(DatabaseError error) {
                                           // Failed to read value
                                           Log.w(TAG, "Failed to read value.", error.toException());
                                           Toast.makeText(choose_task.this, "Failed to read value",
                                                   Toast.LENGTH_SHORT).show();
                                       }
       });
       // Read from the database
       taskTwoReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               String value = dataSnapshot.getValue(String.class);
               Log.d(TAG, "Value is: " + value);

               task2.setText(value);
               LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutTasks);
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               ll.addView(task2, lp);
           }

           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
               Log.w(TAG, "Failed to read value.", error.toException());
               Toast.makeText(choose_task.this, "Failed to read value",
                       Toast.LENGTH_SHORT).show();
           }
       });

       // Read from the database
       taskThreeReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               String value = dataSnapshot.getValue(String.class);
               Log.d(TAG, "Value is: " + value);

               task3.setText(value);
               LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutTasks);
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               ll.addView(task3, lp);
           }


           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
               Log.w(TAG, "Failed to read value.", error.toException());
               Toast.makeText(choose_task.this, "Failed to read value",
                       Toast.LENGTH_SHORT).show();
           }
       });
       // Read from the database
       taskFourReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               String value = dataSnapshot.getValue(String.class);
               Log.d(TAG, "Value is: " + value);

               task4.setText(value);
               LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutTasks);
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               ll.addView(task4, lp);
           }

           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
               Log.w(TAG, "Failed to read value.", error.toException());
               Toast.makeText(choose_task.this, "Failed to read value",
                       Toast.LENGTH_SHORT).show();
           }
       });
       // Read from the database
       taskFiveReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated.
               String value = dataSnapshot.getValue(String.class);
               Log.d(TAG, "Value is: " + value);

               task5.setText(value);
               LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutTasks);
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
               ll.addView(task5, lp);
           }

           @Override
           public void onCancelled(DatabaseError error) {
               // Failed to read value
               Log.w(TAG, "Failed to read value.", error.toException());
           }
       });
   }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

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

   public void openTask(View view)
   {
       Intent newIntent = new Intent(this, ListTasks.class);
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
       Toast helpMessage = Toast.makeText(getApplicationContext(),"Choose daily tasks to complete!",Toast.LENGTH_LONG);
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
}
