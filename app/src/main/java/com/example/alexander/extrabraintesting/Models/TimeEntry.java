package com.example.alexander.extrabraintesting.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class TimeEntry implements Parcelable
{

    public static final String DAY = "day";
    private String day;
    private int id;
    private String title;
    private int duration;
    private String charging;
    private String project;

    private static final String ID = "id";
    private static final String DURATION = "duration";
    private static final String PROJECT_TITLE = "project";
    private static final String TITLE = "title";
    private static final String CHARGING = "charging";

    public static final String PARCELABLE_TIME_ENTRY ="PARCELABLE_TIME_ENTRY";

    public TimeEntry(int id, String title, int duration, String charging, String project)
    {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.charging = charging;
        this.project = project;
    }

    public TimeEntry(JSONObject timeEntry )
    {
        try
        {
            id = timeEntry.getInt(ID);
            title = timeEntry.getString(TITLE);
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
    public String getTitle()
    {
        return title;
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
    public void setTitle(String title)
    {
        this.title = title;
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
        dest.writeString(getTitle());
        dest.writeString(getProject());
        dest.writeString(getCharging());
        dest.writeString(getDay());
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private TimeEntry(Parcel in)
    {
        setId(in.readInt());
        setDuration(in.readInt());
        setTitle(in.readString());
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
}
