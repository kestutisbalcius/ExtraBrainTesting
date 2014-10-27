package com.example.alexander.extrabraintesting.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alexander.extrabraintesting.Models.TimeEntries;
import com.example.alexander.extrabraintesting.Models.User;
import com.example.alexander.extrabraintesting.R;

import java.util.ArrayList;

public class CreateEntriesActivity extends Activity {
    // Input text
    EditText CreateTitle, CreateTask, CreateDuration;
    // Spinner element
    Spinner CreateProject, CreateCharging;
    ArrayList<TimeEntries> entriesProject,entriesCharging;
    ArrayList<String> Charginglist, Projectlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entries_create);

        // Input text
        CreateTitle = (EditText) findViewById(R.id.createTitle);
        CreateTask = (EditText) findViewById(R.id.createTask);
        CreateDuration = (EditText) findViewById(R.id.createDuration);

        // OnItemSelectedListener on the spinner
        // CreateProject.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        // CreateCharging.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Create an array to populate the spinner
        Charginglist = new ArrayList<String>();
        Projectlist = new ArrayList<String>();

        // Spinner element
        CreateProject = (Spinner) findViewById(R.id.createProject);
        CreateCharging = (Spinner) findViewById(R.id.createCharging);

        // Populate spinner with Project and Charging names
        // entriesProject.add(jsonobject.optString("Project"));
        // entriesCharging.add(jsonobject.optString("Charging"));

        // Spinner adapter
        CreateProject.setAdapter(new ArrayAdapter<String>(CreateEntriesActivity.this, android.R.layout.simple_spinner_dropdown_item, Charginglist));
        CreateCharging.setAdapter(new ArrayAdapter<String>(CreateEntriesActivity.this, android.R.layout.simple_spinner_dropdown_item, Projectlist));
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
                // save and make a new TimeEntries with value from editext
                Toast.makeText(getBaseContext(), "Save and make a new TimeEntries with value from editext", Toast.LENGTH_SHORT).show();
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
