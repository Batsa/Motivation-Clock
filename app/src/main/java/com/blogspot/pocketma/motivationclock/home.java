
package com.blogspot.pocketma.motivationclock;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;


public class home extends AppCompatActivity {
    private GoogleApiClient mGoogleApiClient;
    private FirebaseDatabase mFirebaseDatabase; // Entry point for our app to access the database
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    // CHANGED
    String arrayName[]={ "Home",
            "Alarm",
            "Bored",
            "Achievements"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // CHANGED
        CircleMenu circleMenu = (CircleMenu)findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#3487a0"),R.drawable.ic_add,R.drawable.ic_remove)
                .addSubMenu(Color.parseColor("#84abb6"),R.drawable.ic_house)
                .addSubMenu(Color.parseColor("#84abb6"),R.drawable.ic_clock)
                .addSubMenu(Color.parseColor("#84abb6"),R.drawable.ic_bored)
                .addSubMenu(Color.parseColor("#84abb6"),R.drawable.ic_star)

                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        switch(index){
                            case(0):
                                break;
                            case(1):
                                Thread timerThread = new Thread(){
                                    public void run(){
                                        try{
                                            sleep(1400);
                                        }catch(InterruptedException e){
                                            e.printStackTrace();
                                        }finally{
                                            Intent intent = new Intent(home.this,alarm_change.class);
                                            startActivity(intent);
                                        }
                                    }
                                };
                                timerThread.start();
                                break;
                            case(2):
                                Thread timerThread2 = new Thread(){
                                    public void run(){
                                        try{
                                            sleep(1400);
                                        }catch(InterruptedException e){
                                            e.printStackTrace();
                                        }finally{
                                            Intent intent = new Intent(home.this,choose_task.class);
                                            startActivity(intent);
                                        }
                                    }
                                };
                                timerThread2.start();
                                break;
                            case(3):
                                Thread timerThread3 = new Thread(){
                                    public void run(){
                                        try{
                                            sleep(1400);
                                        }catch(InterruptedException e){
                                            e.printStackTrace();
                                        }finally{
                                            Intent intent = new Intent(home.this,achievements.class);
                                            startActivity(intent);
                                        }
                                    }
                                };
                                timerThread3.start();
                                break;
                        }
                        Toast.makeText(home.this,"You selected " +arrayName[index], Toast.LENGTH_SHORT).show();
                    }
                });



        Button signOutButton = (Button) findViewById(R.id.button_sign_out);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("140407439603-dr3v7memtldndm4r50r6gbufj9cqv9ua.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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

    // Redirects the user to the team website when the help button is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_name) {//for help "?" button
            //this opens up the google terms and conditions request (on init web use), then confirms your google login, and then takes you to the website
            Intent action = new Intent(Intent.ACTION_VIEW,Uri.parse("http://pocketma.blogspot.com/p/faq.html"));
            startActivity(action);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out

        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });

    }

    protected void updateUI(FirebaseUser currentUser)
    {
        if (currentUser == null)
        {
            startActivity(new Intent(home.this, login.class));
        }
    }
}
