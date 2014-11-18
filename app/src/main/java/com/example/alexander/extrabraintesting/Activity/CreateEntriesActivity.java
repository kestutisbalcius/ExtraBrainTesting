package com.example.alexander.extrabraintesting.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class CreateEntriesActivity extends Activity {
    RelativeLayout RelativeLayout;
    EditText CreateTitle, CreateTask;
    Spinner CreateProject, CreateCharging;
    NumberPicker days, hours, minutes;
    TimeEntry activityEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_create);
        activityEntry = getIntent().getParcelableExtra(TimeEntry.PARCELABLE_TIME_ENTRY);
        CreateTitle = (EditText) findViewById(R.id.changeTitle);            // Input createTitle
        CreateTask = (EditText) findViewById(R.id.changeTask);              // Input createTask
        CreateCharging = (Spinner) findViewById(R.id.changeCharging);       // Spinner createCharging
        days = (NumberPicker) findViewById(R.id.changeDays);                // NumberPicker Days
        hours = (NumberPicker) findViewById(R.id.changeHours);              // NumberPicker Hours
        minutes = (NumberPicker) findViewById(R.id.changeMinutes);          // NumberPicker Minutes

        days.setMaxValue(365);
        hours.setMaxValue(23);
        minutes.setMaxValue(59);

        //Add items into spinner chargingList
        ArrayList<String> chargingList = new ArrayList<String>();
        chargingList.add("Project 1");
        chargingList.add("Project 2");
        chargingList.add("Project 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CreateCharging.setAdapter(dataAdapter);

        //Add items into spinner projectList
        ArrayList<String> projectList = new ArrayList<String>();
        projectList.add("Project 1");
        projectList.add("Project 2");
        projectList.add("Project 3");
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
                if (
                    CreateTitle.getText().toString().isEmpty() ||
                    CreateTask.getText().toString().isEmpty() ||
                    CreateProject.getSelectedItem().toString().isEmpty() ||
                    CreateCharging.getSelectedItem().toString().isEmpty()) {
                    //If field is empty, make toast
                    Toast.makeText(this, "Save saveMinutes ", Toast.LENGTH_LONG).show();
                } else {
                    String getTextTitle = CreateTitle.getText().toString();
                    String getTextTask = CreateTask.getText().toString();
                    String getTextProject = CreateProject.getSelectedItem().toString();
                    String getTextCharging = CreateCharging.getSelectedItem().toString();

                    //  int saveDays, saveHours, saveMinutes = getValue
                    double valueOfDays = days.getValue();
                    double valueOfHours = hours.getValue();
                    double valueOfMinutes = minutes.getValue();
                    double secondsDuration = valueOfDays * 24 * 60 * 60 + valueOfHours * 60 * 60 + valueOfMinutes * 60;
                    double minutesDuration = secondsDuration / 60;
                    double hoursDuration = secondsDuration / 60 / 60;
                    double daysDuration = secondsDuration / 60 / 60 / 24;

                    Toast.makeText(this, "Duration of by seconds " + secondsDuration,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Duration of by minutes " + minutesDuration,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Duration of by hours " + hoursDuration,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Duration of by days " + daysDuration,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Save saveTitle " + getTextTitle,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Save saveTask " + getTextTask,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Save saveProject " + getTextProject,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Save saveCharging " + getTextCharging,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Save saveDays " + valueOfDays,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Save saveHours " + valueOfHours,  Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Save saveMinutes " + valueOfMinutes,  Toast.LENGTH_LONG).show();
                    finish();
                }
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
