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
import com.example.alexander.extrabraintesting.Handler.TimeEntryDelete;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class ChangeEntriesActivity extends Activity implements OnTimeEntryDeleted {
    EditText changeTitle, changeTask;
    Spinner changeProject, changeCharging;
    NumberPicker changeDays, changeHours, changeMinutes;
    TimeEntry activityEntry;
    public static final int EDIT_OR_REMOVE_TIME_ENTRY = 77;

    public static final String PARCELABLE_TIME_ENTRY = "Is a parcelable TimeEntry";

    public static final String REMOVING_TIME_ENTRY = "Removing the timeEntry";
    public static final String EDITING_TIME_ENTRY = "Editing the timeEntry";

    public static final String TIME_ENTRY_ID = "Remove this TimeEntry";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEntry = getIntent().getParcelableExtra(TimeEntry.PARCELABLE_TIME_ENTRY);
        setContentView(R.layout.activity_entries_change);
        // EditText
        changeTitle = (EditText) findViewById(R.id.changeTitle);            // findViewById EditText ChangeTitle
        changeTask = (EditText) findViewById(R.id.changeTask);              // findViewById EditText ChangeTask

        // timeDurationConversion "method"
        timeDurationConversion(activityEntry.getDuration());

        // Spinner
        chargingArrayList(activityEntry.getCharging(), activityEntry.getCharging());

        // edits
        changeTitle.setText(activityEntry.getTitle());


    }

    private void chargingArrayList(String chargingListBETA, String chargingSelection) {
        changeCharging = (Spinner) findViewById(R.id.changeCharging);       // findViewById Spinner ChangeCharging
        ArrayList<String> chargingList = new ArrayList<String>();
        chargingList.add("according to project");
        chargingList.add("Pay per hour");
        chargingList.add("Fixed price");
        chargingList.add("Internal");
        chargingList.add("Not chargeable");
        chargingList.add("Internal: Sales");
        chargingList.add("Internal: Support");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCharging.setAdapter(dataAdapter);
        changeCharging.setSelection(dataAdapter.getPosition(chargingSelection));
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
                activityEntry.setTitle(changeTitle.getText().toString());
                sendBackResult();
                return true;
            case R.id.remove_entry:
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

    public void sendBackResult()
    {
        Intent editedTimeEntry = getIntent();
        editedTimeEntry.putExtra(EDITING_TIME_ENTRY, true);
        editedTimeEntry.putExtra(PARCELABLE_TIME_ENTRY, activityEntry);
        setResult(RESULT_OK, editedTimeEntry);
        finish();
    }

    @Override
    public void onTimeEntryDeleted(int deleted)
    {
        Intent deletedResult = getIntent();
        deletedResult.putExtra(REMOVING_TIME_ENTRY, true);
        deletedResult.putExtra(TIME_ENTRY_ID, deleted);
        setResult(RESULT_OK, deletedResult);
        finish();
    }
}
