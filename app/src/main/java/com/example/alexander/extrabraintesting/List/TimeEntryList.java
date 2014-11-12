package com.example.alexander.extrabraintesting.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.alexander.extrabraintesting.Models.TimeEntry;

import java.util.ArrayList;

/**
 * Created by Alexander on 2014-11-12.
 */
public class TimeEntryList extends ArrayList<TimeEntry> implements Parcelable
{
    public TimeEntryList(Parcel in)
    {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(size());

        for (TimeEntry timeEntry : this)
        {
            dest.writeInt(timeEntry.getId());
            dest.writeString(timeEntry.getTitle());
            dest.writeInt(timeEntry.getDuration());
            dest.writeString(timeEntry.getCharging());
            dest.writeString(timeEntry.getProject());
        }
    }

    private void readFromParcel(Parcel in)
    {
        // Empty list
        clear();

        int size = in.readInt();

        for (int i = 0; i < size; i++)
        {
            add(new TimeEntry(in.readInt(),in.readString(),in.readInt(),in.readString(),in.readString()));
        }
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
}
