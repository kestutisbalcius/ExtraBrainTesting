package com.example.alexander.extrabraintesting.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.alexander.extrabraintesting.Callbacks.OnTimeEntryCreated;
import com.example.alexander.extrabraintesting.Handler.TimeEntryCreate;
import com.example.alexander.extrabraintesting.Models.TimeEntry;
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
    RelativeLayout relativeLayoutColor;
    private View v;
    private boolean hasFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_create);
        // EditText
        createDescription = (EditText) findViewById(R.id.createDescription);            // findViewById EditText ChangeTitle
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
        setChargingArrayList("According to project");

        // Disabling of EditText in android
        createTask = (EditText) findViewById(R.id.createTask);
        createTask.setEnabled(false);
        createTask.setInputType(InputType.TYPE_NULL);

        relativeLayoutColor = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayoutColor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        createDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private Object setChargingArrayList(Object selectedItem) {
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
        createCharging.setAdapter(dataAdapter);
        createCharging.setSelection(dataAdapter.getPosition(String.valueOf(selectedItem)));

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
