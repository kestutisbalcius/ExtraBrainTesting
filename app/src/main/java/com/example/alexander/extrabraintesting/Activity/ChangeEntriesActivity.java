package com.example.alexander.extrabraintesting.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class ChangeEntriesActivity extends Activity {
    android.widget.RelativeLayout RelativeLayout;
    // Input text
    EditText CreateTitle, CreateTask, CreateDuration;
    // Spinner element
    Spinner CreateProject, CreateCharging;
    //Add items into spinner dynamically
    ArrayList<String> chargingList = new ArrayList<String>();
    ArrayList<String> projectList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_create);
        RelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        CreateTitle = (EditText) findViewById(R.id.createTitle);            // Input createTitle
        CreateTask = (EditText) findViewById(R.id.createTask);              // Input createTask
        CreateDuration = (EditText) findViewById(R.id.createDuration);      // Input createDuration
        CreateProject = (Spinner) findViewById(R.id.createProject);         // Spinner createProject
        CreateCharging = (Spinner) findViewById(R.id.createCharging);       // Spinner createCharging

        //Add items into spinner dynamically
        chargingList.add("Project 1");
        chargingList.add("Project 2");
        chargingList.add("Project 3");
        projectList.add("Project 1");
        projectList.add("Project 2");
        projectList.add("Project 3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CreateCharging.setAdapter(dataAdapter);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, projectList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CreateProject.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entries, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.save_changes_entries:
                if(CreateTitle.getText().toString().isEmpty() ||CreateTask.getText().toString().isEmpty() ||
                    // CreateDuration.getText().toString().isEmpty() ||
                    CreateProject.getSelectedItem().toString().isEmpty() ||CreateCharging.getSelectedItem().toString().isEmpty())
                {
                    //If field is empty, make toast
                    Toast.makeText(this, "isEmpty", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Get all edit text values and set them to a new Contact
                    String saveTitle = CreateTitle.getText().toString();
                    String saveTask = CreateTask.getText().toString();
                    String saveDuration = CreateDuration.getText().toString();
                    String saveProject = CreateProject.getSelectedItem().toString();
                    String saveCharging = CreateCharging.getSelectedItem().toString();
                    //Get all logs of entries text values
                    Log.d("CreateTitle.getText().toString()", saveTitle);
                    Log.d("CreateTask.getText().toString();", saveTask);
                    Log.d("CreateDuration.getText().toString();", saveDuration);
                    Log.d("CreateProject.getSelectedItem().toString();", saveProject);
                    Log.d("CreateCharging.getSelectedItem().toString();", saveCharging);
                }
                // save and make a new TimeEntries with value from editext
                Toast.makeText(getBaseContext(), "Save and make a new TimeEntries with value from EditText and spinners", Toast.LENGTH_SHORT).show();
                finish();
                return true;

            case R.id.Logout:
                // LogOut from the app to get back to the login screen.
                Toast.makeText(getBaseContext(), "LogOut from the app to get back to the login screen.", Toast.LENGTH_SHORT).show();
                User.logOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
