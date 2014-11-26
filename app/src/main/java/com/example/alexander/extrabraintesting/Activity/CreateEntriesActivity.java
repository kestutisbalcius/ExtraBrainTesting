package com.example.alexander.extrabraintesting.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryCreated;
import com.example.alexander.extrabraintesting.Handler.TimeEntryCreate;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class CreateEntriesActivity extends Activity implements OnTimeEntryCreated{
    EditText createDescription, createTask;
    Spinner createCharging;
    NumberPicker createDays;
    NumberPicker createHours;
    NumberPicker createMinutes;
    public static final int REQUEST_CREATE_TIME_ENTRY = 88;
    private TimeEntry timeEntryNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_create);
        // EditText
        createDescription = (EditText) findViewById(R.id.createDescription);            // findViewById EditText ChangeTitle
        createTask = (EditText) findViewById(R.id.createTask);                          // findViewById EditText ChangeTask
        createCharging = (Spinner) findViewById(R.id.createCharging);                   // findViewById Spinner ChangeCharging
        // NumberPicker
        createDays = (NumberPicker) findViewById(R.id.createDays);          // findViewById NumberPicker ChangeDays
        createHours = (NumberPicker) findViewById(R.id.createHours);        // findViewById NumberPicker ChangeHours
        createMinutes = (NumberPicker) findViewById(R.id.createMinutes);    // findViewById NumberPicker ChangeMinutes
        // NumberPicker
        createDays = (NumberPicker) findViewById(R.id.createDays);          // findViewById NumberPicker ChangeDays
        createHours = (NumberPicker) findViewById(R.id.createHours);        // findViewById NumberPicker ChangeHours
        createMinutes = (NumberPicker) findViewById(R.id.createMinutes);    // findViewById NumberPicker ChangeMinutes
        // setMaxValue maybe change it to 23-24 and 59-60
        createDays.setMaxValue(365);                                        // setMaxValue(365); NumberPicker ChangeDays
        createHours.setMaxValue(23);                                        // setMaxValue(23); NumberPicker ChangeHours
        createMinutes.setMaxValue(59);                                      // setMaxValue(59); NumberPicker ChangeMinutes
        createDays.setValue(0);
        createHours.setValue(0);
        createMinutes.setValue(0);
        // Spinner chargingArrayList "method"
        setChargingArrayList("inherit_from_project");

    }
    private Object setChargingArrayList(Object selectedItem) {
        ArrayList<String> chargingList = new ArrayList<String>();
        chargingList.add("inherit_from_project");
        chargingList.add("pay_per_hour");
        chargingList.add("fixed");
        chargingList.add("internal");
        chargingList.add("not_chargeable");
        chargingList.add("sales");
        chargingList.add("support");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        createCharging.setAdapter(dataAdapter);
        createCharging.setSelection(dataAdapter.getPosition(String.valueOf(selectedItem)));
        return selectedItem;
    }

    private int setTimeDuration(int changeDays, int changeHours, int changeMinutes) {
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

                timeEntryNew = new TimeEntry();
                // editText setDescription "Text"
                timeEntryNew.setDescription(createDescription.getText().toString());
                // Spinner setChargingArrayList "method"
                String Charging = (String) setChargingArrayList(createCharging.getSelectedItem());
                timeEntryNew.setCharging(Charging);
                Log.v("Charging = ", String.valueOf(Charging));
                // NumberPicker setTimeDuration "method"
                int timeDuration = setTimeDuration(createDays.getValue(), createHours.getValue(), createMinutes.getValue());
                timeEntryNew.setDuration(timeDuration);
                Log.v("Duration = ", String.valueOf(timeDuration));

                TimeEntryCreate timeEntryCreate = new TimeEntryCreate(this, timeEntryNew);
                timeEntryCreate.execute();
                // TimeEntryUpdate timeEntryUpdate = new TimeEntryUpdate(this, activityEntry);
                // timeEntryUpdate.execute();
                // sendBackResult();
                return true;
            case R.id.remove_entry:
                // TimeEntryDelete timeEntryDelete = new TimeEntryDelete(this,activityEntry.getId());
                // timeEntryDelete.execute();
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
        getMenuInflater().inflate(R.menu.create_entries, menu);
        return true;
    }

    public void sendBackResult(TimeEntry timeEntry)
    {
        Intent createTimeEntry = getIntent();
        createTimeEntry.putExtra(TimeEntry.PARCELABLE_TIME_ENTRY, timeEntry);
        setResult(RESULT_OK, createTimeEntry);
        finish();
    }

    @Override
    public void onTimeEntryCreated(TimeEntry timeEntryFromApi)
    {
        Log.d("onTimeEntryCreated: ","success!");
        sendBackResult(timeEntryFromApi);
    }
}
