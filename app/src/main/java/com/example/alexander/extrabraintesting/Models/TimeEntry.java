package com.example.alexander.extrabraintesting.Models;

import android.util.Log;

import com.example.alexander.extrabraintesting.Handlers.TimeEntryDelete;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeEntry
{
    private int id;
    private int duration;
    private String project;
    private String title;
    private String charging;

    private static final String ID = "id";
    private static final String DURATION = "duration";
    private static final String PROJECT_TITLE = "project";
    private static final String TITLE = "title";
    private static final String CHARGING = "charging";

    public TimeEntry(JSONObject timeEntry)
    {
        try
        {
            id = timeEntry.getInt(ID);
            duration = timeEntry.getInt(DURATION);
            title = timeEntry.getString(TITLE);
            charging = timeEntry.getString(CHARGING);

            // Sets NULL if no value
            project = timeEntry.optString(PROJECT_TITLE);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String getFormattedDuration()
    {
        // Minute in seconds
        int MINUTE = 60;
        // Hour in minutes
        int HOUR = 60;
        // Day in hours
        int DAY = 24;

        // Whole minutes in total
        int minutesTotal = duration / MINUTE;
        // Remaining seconds
//        int seconds = duration % MINUTE;

        // Whole hours in total
        int hoursTotal = minutesTotal / HOUR;
        // Minutes remaining
        int minutes = minutesTotal % HOUR;

        // Whole days in total
//        int daysTotal = hoursTotal / DAY;
        // Hours remaining
        int hours = hoursTotal % DAY;

        return String.format("%02d:%02d", hours, minutes);
    }

    public String getProject()
    {
        return project;
    }

    public String getTitle()
    {
        return title;
    }

    public String getCharging()
    {
        return charging;
    }

    public int getId()
    {
        return id;
    }
}
