package com.example.alexander.extrabraintesting.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeEntry
{
    private String duration;
    private String project;
    private String title;
    private String charging;

    private static final String DURATION = "duration";
    private static final String PROJECT_TITLE = "project";
    private static final String TITLE = "title";
    private static final String CHARGING = "charging";

    public TimeEntry(JSONObject timeEntry)
    {
        try
        {
            duration = timeEntry.getString(DURATION);
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

    public String getDuration()
    {
        return duration;
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
}
