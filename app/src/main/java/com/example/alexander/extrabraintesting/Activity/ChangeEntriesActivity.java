package com.example.alexander.extrabraintesting.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryDeleted;
import com.example.alexander.extrabraintesting.Handlers.TimeEntryDelete;
import com.example.alexander.extrabraintesting.Models.TimeEntry;

import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class ChangeEntriesActivity extends Activity implements OnTimeEntryDeleted {
    EditText changeTitle, changeTask;
    Spinner changeProject, changeCharging;
    NumberPicker changeDays, changeHours, changeMinutes;
    public static final int EDIT_OR_REMOVE_TIME_ENTRY = 77;
    public static final String REMOVING_TIME_ENTRY = "Removing the timeEntry";
    public static final String REMOVED_THIS_TIME_ENTRY_ID = "Remove this TimeEntry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeEntry activityEntry = getIntent().getParcelableExtra(TimeEntry.PARCELABLE_TIME_ENTRY);
        setContentView(R.layout.activity_entries_change);
        // EditText
        changeTitle = (EditText) findViewById(R.id.changeTitle);            // findViewById EditText ChangeTitle
        changeTask = (EditText) findViewById(R.id.changeTask);              // findViewById EditText ChangeTask
        // Spinner
        changeProject = (Spinner) findViewById(R.id.changeProject);         // findViewById Spinner ChangeProject
        changeCharging = (Spinner) findViewById(R.id.changeCharging);       // findViewById Spinner ChangeCharging

        timeDurationConversion(activityEntry.getDuration());
        changeTitle.setText(activityEntry.getTitle());
        activityEntry.setTitle(changeTitle.getText().toString());

        ArrayList<String> chargingList = new ArrayList<String>();
        chargingList.add("Project 1");
        chargingList.add("Project 2");
        chargingList.add("Project 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCharging.setAdapter(dataAdapter);

        ArrayList<String> projectList = new ArrayList<String>();
        projectList.add("Project 1");
        projectList.add("Project 2");
        projectList.add("Project 3");
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, projectList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeProject.setAdapter(dataAdapter);
    }

    private void timeDurationConversion(int seconds) {
        final int HOUR_IN_AN_Days = 24;
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = seconds / SECONDS_IN_A_MINUTE;
//        seconds -= minutes * SECONDS_IN_A_MINUTE;
        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;

        int days = hours / HOUR_IN_AN_Days;
        hours -= days * HOUR_IN_AN_Days;

        // NumberPicker
        changeDays = (NumberPicker) findViewById(R.id.changeDays);          // findViewById NumberPicker ChangeDays
        changeHours = (NumberPicker) findViewById(R.id.changeHours);        // findViewById NumberPicker ChangeHours
        changeMinutes = (NumberPicker) findViewById(R.id.changeMinutes);    // findViewById NumberPicker ChangeMinutes
        // setMaxValue maybe change it to 23-24 and 59-60
        changeDays.setMaxValue(365);                                        // setMaxValue(365); NumberPicker ChangeDays
        changeHours.setMaxValue(23);                                        // setMaxValue(23); NumberPicker ChangeHours
        changeMinutes.setMaxValue(59);                                      // setMaxValue(59); NumberPicker ChangeMinutes
        changeDays.setValue(days);
        changeHours.setValue(hours);
        changeMinutes.setValue(minutes);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.save_changes_entries:
                    finish();

            case R.id.remove_entry:
                TimeEntry activityEntry = getIntent().getParcelableExtra(TimeEntry.PARCELABLE_TIME_ENTRY);
                TimeEntryDelete timeEntryDelete = new TimeEntryDelete(this,activityEntry.getId());
                timeEntryDelete.execute();
                return true;
            case R.id.Logout:
                // LogOut from the app to get back to the login screen.
                Toast.makeText(getBaseContext(), "LogOut from the app to get back to the login screen.", Toast.LENGTH_SHORT).show();
                User.logOut();
                Intent IntentlogOut = new Intent(this, LoginActivity.class);
                startActivity(IntentlogOut);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.entries, menu);
        return true;
    }

    @Override
    public void onTimeEntryDeleted(int deleted)
    {
        Intent deletedResult = getIntent();
        deletedResult.putExtra(REMOVING_TIME_ENTRY, true);
        deletedResult.putExtra(REMOVED_THIS_TIME_ENTRY_ID, deleted);
        setResult(RESULT_OK, deletedResult);
        finish();
    }
}
