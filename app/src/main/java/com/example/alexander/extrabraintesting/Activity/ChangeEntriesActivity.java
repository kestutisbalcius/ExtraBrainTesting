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

import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class ChangeEntriesActivity extends Activity {
    RelativeLayout RelativeLayout;
    EditText ChangeTitle, ChangeTask;
    Spinner ChangeProject, ChangeCharging;
    NumberPicker ChangeDays, ChangeHours, ChangeMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_change);

        RelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);// findViewById RelativeLayout RelativeLayout
        ChangeTitle = (EditText) findViewById(R.id.createTitle);            // findViewById EditText ChangeTitle
        ChangeTask = (EditText) findViewById(R.id.createTask);              // findViewById EditText ChangeTask
        ChangeProject = (Spinner) findViewById(R.id.createProject);         // findViewById Spinner ChangeProject
        ChangeCharging = (Spinner) findViewById(R.id.createCharging);       // findViewById Spinner ChangeCharging
        ChangeDays = (NumberPicker) findViewById(R.id.Days);                // findViewById NumberPicker ChangeDays
        ChangeHours = (NumberPicker) findViewById(R.id.Hours);              // findViewById NumberPicker ChangeHours
        ChangeMinutes = (NumberPicker) findViewById(R.id.Minutes);          // findViewById NumberPicker ChangeMinutes

        ChangeDays.setMaxValue(365);                                        // setMaxValue(365); NumberPicker ChangeDays
        ChangeHours.setMaxValue(23);                                        // setMaxValue(23); NumberPicker ChangeHours
        ChangeMinutes.setMaxValue(59);                                      // setMaxValue(59); NumberPicker ChangeMinutes

        ArrayList<String> chargingList = new ArrayList<String>();           // ArrayList<String> chargingList = new ArrayList<String>();
        chargingList.add("Project 1");
        chargingList.add("Project 2");
        chargingList.add("Project 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chargingList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ChangeCharging.setAdapter(dataAdapter);

        ArrayList<String> projectList = new ArrayList<String>();                 //Add items into spinner projectList
        projectList.add("Project 1");
        projectList.add("Project 2");
        projectList.add("Project 3");
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, projectList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ChangeProject.setAdapter(dataAdapter);
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
                    ChangeTitle.getText().toString().isEmpty() ||
                    ChangeTask.getText().toString().isEmpty() ||
                    ChangeProject.getSelectedItem().toString().isEmpty() ||
                    ChangeCharging.getSelectedItem().toString().isEmpty())
                {
                    //If field is empty, make toast
                    Toast.makeText(getBaseContext(), "They are empty field", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String getTextTitle = ChangeTask.getText().toString();
                    String getTextTask = ChangeTask.getText().toString();
                    String getTextProject = ChangeProject.getSelectedItem().toString();
                    String getTextCharging = ChangeCharging.getSelectedItem().toString();
                    double valueOfDays = ChangeDays.getValue();
                    double valueOfHours = ChangeHours.getValue();
                    double valueOfMinutes = ChangeMinutes.getValue();
                    double secondsDuration = valueOfDays * 24 * 60 * 60 + valueOfHours * 60 * 60 + valueOfMinutes * 60;
                    double minutesDuration = secondsDuration / 60;
                    double hoursDuration = secondsDuration / 60 / 60;
                    double daysDuration = secondsDuration / 60 / 60 / 24;
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
