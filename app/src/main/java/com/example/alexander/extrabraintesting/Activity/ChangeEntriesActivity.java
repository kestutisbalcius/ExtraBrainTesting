package com.example.alexander.extrabraintesting.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryDeleted;
import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryUpdated;
import com.example.alexander.extrabraintesting.Handler.TimeEntryDelete;
import com.example.alexander.extrabraintesting.Handler.TimeEntryUpdate;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class ChangeEntriesActivity extends Activity implements OnTimeEntryDeleted, OnTimeEntryUpdated
{
    EditText changeDescription, changeTask;
    Spinner changeProject, changeCharging;
    NumberPicker changeDays;
    NumberPicker changeHours;
    NumberPicker changeMinutes;
    TimeEntry activityEntry;
    public static final int REQUEST_EDIT_OR_REMOVE_TIME_ENTRY = 77;
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
        changeDescription = (EditText) findViewById(R.id.changeDescription);            // findViewById EditText ChangeTitle
        changeCharging = (Spinner) findViewById(R.id.changeCharging);                   // findViewById Spinner ChangeCharging
        // timeDurationConversion "method"
        getTimeDuration(activityEntry.getDuration());
        // Spinner chargingArrayList "method"
        getChargingArrayList(activityEntry.getCharging());
        // edits "Getter"
        changeDescription.setText(activityEntry.getDescription());
        // Disabling of EditText in android
        changeTask = (EditText) findViewById(R.id.changeTask);
        changeTask.setEnabled(false);
        changeTask.setInputType(InputType.TYPE_NULL);
    }

    private Object setChargingArrayList(Object selectedItem)
    {
        ArrayList<String> chargingList = new ArrayList<String>();
        chargingList.add("According to project");
        chargingList.add("Pay per hour");
        chargingList.add("Fixed price");
        chargingList.add("Internal");
        chargingList.add("Not chargeable");
        chargingList.add("Internal: Sales");
        chargingList.add("Internal: Support");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCharging.setAdapter(dataAdapter);
        changeCharging.setSelection(dataAdapter.getPosition(String.valueOf(selectedItem)));
        if (selectedItem == "According to project")
        {
            selectedItem = "inherit_from_project";
        }

        if (selectedItem == "Pay per hour")
        {
            selectedItem = "pay_per_hour";
        }

        if (selectedItem == "Fixed price")
        {
            selectedItem = "fixed";
        }

        if (selectedItem == "Internal")
        {
            selectedItem = "internal";
        }

        if (selectedItem == "Not chargeable")
        {
            selectedItem = "not_chargeable";
        }

        if (selectedItem == "Internal: Sales")
        {
            selectedItem = "sales";
        }

        if (selectedItem == "Internal: Support")
        {
            selectedItem = "support";
        }
        return selectedItem;
    }

    private void getChargingArrayList(String chargingSelection)
    {
        ArrayList<String> chargingList = new ArrayList<String>();
        chargingList.add("According to project");
        chargingList.add("Pay per hour");
        chargingList.add("Fixed price");
        chargingList.add("Internal");
        chargingList.add("Not chargeable");
        chargingList.add("Internal: Sales");
        chargingList.add("Internal: Support");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeCharging.setAdapter(dataAdapter);
        changeCharging.setSelection(dataAdapter.getPosition(String.valueOf(chargingSelection)));
    }

    private void getTimeDuration(int seconds) {
        final int HOUR_IN_AN_Days = 24;
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = seconds / SECONDS_IN_A_MINUTE;
        seconds -= minutes * SECONDS_IN_A_MINUTE;
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

    private int setTimeDuration(int changeDays, int changeHours, int changeMinutes)
    {
        final int SECONDS_IN_AN_Days = 60 * 60 * 24;
        final int SECONDS_IN_AN_HOUR = 60 * 60;
        final int SECONDS_IN_A_MINUTE = 60;
        int durationOfDays = changeDays * SECONDS_IN_AN_Days;
        int durationOfHours = changeHours * SECONDS_IN_AN_HOUR;
        int durationOfMinutes = changeMinutes * SECONDS_IN_A_MINUTE;
        return + durationOfDays + durationOfHours + durationOfMinutes;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.save_changes_entries:
                // editText setDescription "Text"
                activityEntry.setDescription(changeDescription.getText().toString());

                // Spinner setChargingArrayList "method"
                String Charging = (String) setChargingArrayList(changeCharging.getSelectedItem());
                activityEntry.setCharging(Charging);
                Log.v("Charging = ", String.valueOf(Charging));


                // NumberPicker setTimeDuration "method"
                int timeDuration = setTimeDuration(changeDays.getValue(), changeHours.getValue(), changeMinutes.getValue());
                activityEntry.setDuration(timeDuration);
                Log.v("Duration = ", String.valueOf(timeDuration));

                TimeEntryUpdate timeEntryUpdate = new TimeEntryUpdate(this, activityEntry);
                timeEntryUpdate.execute();
                // sendBackResult();

                return true;
            case R.id.remove_entry:
                TimeEntryDelete timeEntryDelete = new TimeEntryDelete(this,activityEntry.getId());
                timeEntryDelete.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_entries, menu);
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

    @Override
    public void onTimeEntryUpdated()
    {
        sendBackResult();
    }
}
