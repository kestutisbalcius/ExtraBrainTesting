package com.example.alexander.extrabraintesting.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeEntry implements Parcelable
{

    private String day;
    private int id;
    private String description;
    private String project;
    private int duration;
    private String charging;
    private int taskId;

    private static final String ID = "id";
    private static final String DURATION = "duration";
    private static final String PROJECT_TITLE = "project";
    public static final String TASK_ID = "task_id";
    private static final String DESCRIPTION = "description";
    private static final String CHARGING = "charging";
    public static final String DAY = "day";
    public static final String PARCELABLE_TIME_ENTRY ="PARCELABLE_TIME_ENTRY";
    public TimeEntry(int id, String description, int duration, String charging, String project)
    {
        this.id = id;
        this.description = description;
        this.duration = duration;
        this.charging = charging;
        this.project = project;
    }

    public TimeEntry(JSONObject timeEntry )
    {
        try
        {
            id = timeEntry.getInt(ID);
            description = timeEntry.getString(DESCRIPTION);
            duration = timeEntry.getInt(DURATION);
            charging = timeEntry.getString(CHARGING);
            day = timeEntry.getString(DAY);

            // Sets NULL if no value
            project = timeEntry.optString(PROJECT_TITLE);
            taskId = timeEntry.optInt(TASK_ID);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public TimeEntry()
    {

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
        int daysTotal = hoursTotal / DAY;
        // Hours remaining
        int hours = hoursTotal % DAY;

        return String.format("%02d:%02d:%02d", daysTotal, hours, minutes );
    }

    public String getFormattedCharging()
    {
        String apiValue = getCharging();

        apiValue = apiValue.replace("_"," ");
        // Initial letter uppercase, rest should be lowercase
        apiValue = apiValue.substring(0,1).toUpperCase() + apiValue.substring(1).toLowerCase();

        return apiValue;
    }

    public JSONObject getJSON()
    {
        JSONObject container = new JSONObject();
        JSONObject time_entry = new JSONObject();
        try
        {
            time_entry.put(ID, id);
            // Preferably the API should only deliver a description independent of where it's from (like task or project)
            time_entry.put(DESCRIPTION, description);
            time_entry.put(DURATION, duration);
            time_entry.put(CHARGING, charging);
            time_entry.put(PROJECT_TITLE, project);
            if (taskId > 0)
            {
                time_entry.put(TASK_ID, taskId);
            }

            container.put("time_entry",time_entry);

            Log.d("TimeEntry JSON", time_entry.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return container;
    }

    public String getCharging()
    {
        return charging;
    }
    public String getProject()
    {
        return project;
    }
    public String getDescription()
    {
        return description;
    }
    public int getDuration()
    {
        return duration;
    }
    public int getId()
    {
        return id;
    }
    public String getDay()
    {
        return day;
    }
    public int getTaskId()
    {
        return taskId;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public void setDuration(int duration)
    {
        this.duration = duration;
    }
    public void setProject(String project)
    {
        this.project = project;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setCharging(String charging)
    {
        this.charging = charging;
    }
    public void setDay(String day)
    {
        this.day = day;
    }
    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    /* everything below here is for implementing Parcelable */
    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents()
    {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(getId());
        dest.writeInt(getDuration());
        dest.writeString(getDescription());
        dest.writeString(getProject());
        dest.writeString(getCharging());
        dest.writeString(getDay());
        dest.writeInt(getTaskId());
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private TimeEntry(Parcel in)
    {
        setId(in.readInt());
        setDuration(in.readInt());
        setDescription(in.readString());
        setProject(in.readString());
        setCharging(in.readString());
        setDay(in.readString());
        setTaskId(in.readInt());
    }

    public static final Parcelable.Creator<TimeEntry> CREATOR = new Parcelable.Creator<TimeEntry>()
    {

        @Override
        public TimeEntry createFromParcel(Parcel source) {
            return new TimeEntry(source);
        }

        @Override
        public TimeEntry[] newArray(int size) {
            return new TimeEntry[size];
        }
    };



}
