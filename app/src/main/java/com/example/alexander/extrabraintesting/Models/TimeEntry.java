package com.example.alexander.extrabraintesting.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeEntry
{
    private static String duration;
    private static String projectTitle;
    private static String title;
    private static String charging;

    private static final String DURATION = "duration";
    private static final String PROJECT_TITLE = "projectTitle";
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
            projectTitle = timeEntry.optString(PROJECT_TITLE);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
