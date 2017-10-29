package com.blogspot.pocketma.motivationclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListTasks extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_list_tasks);
   }

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
}
