package com.example.alexander.extrabraintesting.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeEntry implements Parcelable
{

    public static final String DAY = "day";
    private String day;
    private int id;
    private String description;
    private String project;
    private int duration;
    private String charging;

    private static final String ID = "id";
    private static final String DURATION = "duration";
    private static final String PROJECT_TITLE = "project";
    private static final String DESCRIPTION = "description";
    private static final String CHARGING = "charging";

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
        int daysTotal = hoursTotal / DAY;
        // Hours remaining
        int hours = hoursTotal % DAY;

        return String.format("%02d:%02d:%02d", daysTotal, hours, minutes );
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

            container.put("time_entry",time_entry);

            Log.d("TimeEntry JSON", time_entry.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return container;
    }

}
